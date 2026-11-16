# MapStruct Incident Response Plan

This document is the playbook MapStruct maintainers follow when an incident is active. It is not a replacement for ordinary bug triage — regular contributions and bug reports still flow through the process described in [`CONTRIBUTING.md`](../CONTRIBUTING.md). For instructions on how to *report* a security vulnerability, see [`SECURITY.md`](../SECURITY.md).

MapStruct is a compile-time Java annotation processor distributed via Maven Central. The incidents this plan covers reflect that reality: immutable published artifacts, a manual release workflow, and a small maintainer team. There is no running service to page someone about at 3am.

## What counts as an incident

Four categories, with a concrete MapStruct-flavored example of each:

* **Security vulnerability / CVE** — e.g. a crafted `@Mapper` input triggers arbitrary code execution in the annotation processor during `javac`, or the generated code contains an injection vulnerability.
* **Critical bug breaking user builds** — e.g. a released `1.x.y` generates non-compiling code for all users of `@Mapping`, with no workaround short of pinning to the previous version.
* **Release / supply-chain issue** — e.g. a bad artifact reaches Maven Central, a release secret is leaked, or `release.yml` fails mid-publish after already pushing version-bump commits to `main`.
* **Infra / CI incident** — e.g. a GitHub Actions workflow blocks all PRs, CodeQL flags a real finding, or the mapstruct.org website is out of sync after a release.

Ordinary user bugs filed as issues are *not* incidents — they follow the normal contribution flow.

## Severity levels

| Severity | Definition | MapStruct example |
|---|---|---|
| **SEV-0** | Active exploitation or supply-chain compromise of a published artifact; users must stop using a version immediately. | Compromised signed JAR on Maven Central; leaked GPG or Sonatype credentials with evidence of misuse; RCE in the annotation processor triggered during `javac`. |
| **SEV-1** | Released version is broken for a majority of users or contains an undisclosed security flaw with no workaround. | A `1.x.y` release generates non-compiling code for all users of `@Mapping`; confirmed CVE with CVSS ≥ 7 but not yet exploited. |
| **SEV-2** | Released version broken for a specific but significant cohort; workaround exists; or lower-severity CVE; or the release pipeline is blocked. | Regression affecting only Java 21 + records; CVE with CVSS < 7; JReleaser fails staging so a release is blocked but nothing published; CI red on `main`. |
| **SEV-3** | Degraded but not blocking. | CodeQL false-positive noise; a single OS matrix leg flaky; mapstruct.org docs out of sync post-release. |

## General response flow

Every incident follows the same six-step spine, regardless of category:

1. **Acknowledge** — confirm the report has been received and is being looked at.
2. **Assess severity** — assign SEV-0 / SEV-1 / SEV-2 / SEV-3 using the table above.
3. **Declare** — open the tracking artifact (GitHub Security Advisory for security issues, pinned issue for regressions, Discussion thread for CI incidents).
4. **Mitigate** — give users a workaround, yank a bad release, or disable a broken workflow — whatever stops the immediate bleeding.
5. **Fix and verify** — land the real fix, add a regression test, cut a patch release if needed, verify the fix on the published artifact.
6. **Close out** — announce resolution on the public channels, credit reporters, update this document if the process needs changing.

The playbooks below are specializations of this spine.

## Playbooks

### A. Security vulnerability / CVE

1. Acknowledge receipt within 48 hours via GitHub Private Vulnerability Reporting.
2. Confirm reproducibility against the latest release and `main`; assign a CVSS score.
3. Open a **draft GitHub Security Advisory (GHSA)** — this is the tracking artifact. Do **not** open a public issue.
4. Request a CVE via the GHSA (GitHub auto-requests from MITRE).
5. Develop the fix on the private fork the advisory creates; maintainers review before merge.
6. Merge the private-fork fix into `main` using the "Merge" button on the advisory page; confirm CI is green on `main` before proceeding.
7. Coordinate a disclosure date with the reporter — target ≤ 90 days from report, sooner for SEV-0.
8. Add a `### Security` entry to `NEXT_RELEASE_CHANGELOG.md`, then cut a patch release from `main`; verify the GPG signature and checksums on the published artifact before publishing the advisory.
9. Publish the GHSA, announce on Discussions + mapstruct.org blog, and credit the reporter unless they decline.

### B. Critical bug breaking user builds

1. Reproduce the bug against the reported version using a minimal `@Mapper` (ideally in `integrationtest/`).
2. Use `git bisect` across tagged releases to find the introducing commit.
3. Open a pinned GitHub issue titled `[REGRESSION <version>] ...`, labeled `regression` and `priority:critical`.
4. Post a user-facing workaround (e.g. pin `<mapstruct.version>` to the prior release) in the issue within 24 hours.
5. Decide between a patch release or a revert-only fix. For SEV-1, target a patch release within 72 hours.
6. Add a regression test in `processor/src/test/` that locks the behavior.
7. Update `NEXT_RELEASE_CHANGELOG.md` under `### Bugs`, run the `release.yml` workflow dispatch, verify the artifact lands on Central before closing the issue.
8. Announce on Discussions; link from the pinned issue.

### C. Release / supply-chain issue

1. **Stop the bleeding** — if `release.yml` is still running, cancel the run from the Actions UI immediately.
2. Assess blast radius: did artifacts reach Maven Central staging only, Central itself, or also the mapstruct.org website?
3. If any secret is suspected compromised, rotate **before** taking any other recovery action: `GPG_PASSPHRASE`, `GPG_PUBLIC_KEY`, `GPG_PRIVATE_KEY`, `SONATYPE_USERNAME`, `SONATYPE_PASSWORD`, `SONATYPE_CENTRAL_USERNAME`, `SONATYPE_CENTRAL_TOKEN`, `GIT_WEBSITE_ACCESS_TOKEN`. Revoke the GPG key via a public keyserver. Rotate in this order: **Sonatype first** (stops further publishes), **GPG second** (revoke publicly), **website PAT last** — see the rotation order guidance in the **Secrets and rotation** section below.
4. If a bad artifact reached Maven Central: **you cannot delete it.** Publish a superseding version immediately (e.g. `1.6.4` to replace a bad `1.6.3`), and mark the bad version yanked via the **Yanked-release procedure** section below.
5. If `main` was corrupted (stray version bump, bad tag): revert commits, recreate the tag, coordinate any force-push with maintainers' explicit agreement.
6. If the website push succeeded but the release failed: manually revert the corresponding commit on the `mapstruct/mapstruct.org` repo.
7. Open a SEV-0 or SEV-1 tracking issue; freeze further releases until the root cause is known.
8. Replay the release from a clean state, verifying signatures at each step, then announce the yank on Discussions and mapstruct.org.

### D. Infra / CI incident

1. Identify scope: single workflow (e.g. `windows.yml`), all workflows, or a GitHub Actions-wide outage.
2. Check [githubstatus.com](https://www.githubstatus.com) — if the cause is upstream, park and monitor.
3. If it is our bug: open an issue labeled `ci`, and disable the affected workflow (comment the `on:` block or gate with `if: false`) to unblock contributors.
4. If contributor PRs are blocked for more than 4 hours, post a status update in Discussions.
5. Root-cause. Common suspects: action version drift, JDK EA breakage, Maven cache corruption, CodeQL rule updates.
6. Fix in a focused PR; require a green run on a throwaway branch before merging.
7. Re-enable the workflow.
8. Escalate to SEV-1 only if the incident is blocking a pending release.

## Yanked-release procedure

Maven Central is **immutable**: once an artifact is published, it cannot be deleted. When a release must be withdrawn, this is the procedure:

1. Publish a superseding version (e.g. `1.6.4` to replace a bad `1.6.3`) as soon as a fix or clean rebuild is ready.
2. If the yank is security-related, open or update the corresponding GitHub Security Advisory (GHSA) for the bad version — the GHSA is the machine-readable record of the security flaw. For non-security yanks (e.g. a broken build regression), skip this step; the `readme.md` banner and blog post (steps 3–4) are the durable record.
3. Add a short banner to `readme.md` listing the yanked version, the reason, and the replacement — e.g. *"⚠ MapStruct 1.6.3 is yanked due to [reason]. Please upgrade to 1.6.4."*
4. Post a notice on the mapstruct.org blog and link it from Discussions.
5. Remove the banner from `readme.md` at the next normal release cycle (the GHSA, if opened, is the durable record for security yanks; the blog post is the durable record for non-security yanks).

## Secrets and rotation

The IRP itself does not store any secret. This section is the **index** of which secrets drive releases and CI, so that a rotation during an incident hits everything in one pass. The live rotation credentials and step-by-step procedures live outside the repo in the maintainers' password manager.

Secrets used by `.github/workflows/release.yml`:

| Secret | Purpose | Rotation path |
|---|---|---|
| `GPG_PASSPHRASE` | Passphrase for the release signing key | Change on a local keyring, update the repo secret, import new passphrase |
| `GPG_PUBLIC_KEY` | ASCII-armored public key for JReleaser | Re-export from local keyring after rotation |
| `GPG_PRIVATE_KEY` | ASCII-armored private key for JReleaser | Generate new key pair, re-export, revoke old key via keyserver |
| `SONATYPE_CENTRAL_USERNAME` | New Sonatype Central Portal username | Rotate token in Sonatype Central Portal |
| `SONATYPE_CENTRAL_TOKEN` | New Sonatype Central Portal token | Rotate token in Sonatype Central Portal |
| `SONATYPE_USERNAME` | Legacy OSSRH / Nexus2 username | Rotate in Sonatype JIRA / legacy portal |
| `SONATYPE_PASSWORD` | Legacy OSSRH / Nexus2 password | Rotate in Sonatype JIRA / legacy portal |
| `GIT_WEBSITE_ACCESS_TOKEN` | PAT for pushing to `mapstruct/mapstruct.org` | Regenerate PAT on the owning account, update repo secret |

Secrets used by `.github/workflows/main.yml`:

| Secret | Purpose | Rotation path |
|---|---|---|
| `SONATYPE_USERNAME` / `SONATYPE_PASSWORD` | Snapshot deploys to OSSRH from `main` | Same as above |
| `CODECOV_TOKEN` | Upload coverage to Codecov | Regenerate on codecov.io, update repo secret |

Rotation order during a suspected compromise: **Sonatype first** (to stop any further publish), **GPG second** (revoke key publicly), **website PAT last** (lowest blast radius).

## Communication channels map

| Incident type | Private tracking | Public acknowledgement | Resolution announcement |
|---|---|---|---|
| Security / CVE | GitHub Private Advisory | Only after the fix is released | GHSA publish + Discussion post + mapstruct.org blog |
| Critical bug | None (public) | Pinned GitHub issue within 24 hours | Issue closed + Discussion post |
| Release / supply chain | Direct maintainer DM (Signal) | Pinned issue + mapstruct.org banner | Discussion post + mapstruct.org blog |
| Infra / CI | None | Discussion thread if contributor-blocking | Close the discussion |

## Maintenance of this document

This document is reviewed annually and after every SEV-0 or SEV-1 incident. Changes are made via normal PRs.
