/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.gem;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

/**
 * Gem for the enum {@link org.mapstruct.ReportingPolicy}.
 *
 * @author Gunnar Morling
 */
public enum ReportingPolicyGem {

    IGNORE( null, false, false ),
    WARN( Kind.WARNING, true, false ),
    ERROR( Kind.ERROR, true, true );

    private final Diagnostic.Kind diagnosticKind;
    private final boolean requiresReport;
    private final boolean failsBuild;

    ReportingPolicyGem(Diagnostic.Kind diagnosticKind, boolean requiresReport, boolean failsBuild) {
        this.requiresReport = requiresReport;
        this.diagnosticKind = diagnosticKind;
        this.failsBuild = failsBuild;
    }

    public Diagnostic.Kind getDiagnosticKind() {
        return diagnosticKind;
    }

    public boolean requiresReport() {
        return requiresReport;
    }

    public boolean failsBuild() {
        return failsBuild;
    }
}
