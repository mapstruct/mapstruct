### Features

* Add support for JSpecify nullness annotations (#1243) - MapStruct now detects `@NonNull`, `@Nullable`, `@NullMarked` and `@NullUnmarked` from `org.jspecify.annotations` to control null check generation:
  - Source `@NonNull` skips null checks; target `@NonNull` always adds them
  - `@NonNull` source parameters skip the method-level null guard
  - `@NonNull` source on collection-typed property mappings skips the wrapping null guard
  - Container mapping methods (`Iterable`, `Map`, `Stream`, arrays) honor JSpecify on their source parameter
  - `@NonNull` mapping-method return type implies `NullValueMappingStrategy.RETURN_DEFAULT` semantics across bean, iterable, map and stream mapping methods
  - `@NullMarked` / `@NullUnmarked` scope is resolved by walking method &rarr; class &rarr; outer class &rarr; package
  - Compile error when mapping a potentially nullable source to a `@NonNull` constructor parameter without a `defaultValue`
  - Can be disabled with the `mapstruct.disableJSpecify` compiler option
* Add `URI` to `String` built-in conversions (#4018)

### Enhancements

* Support `SET_TO_NULL` for overloaded target methods, requiring a cast (#3949)
* Use multi-catch in generated code (#4021)
* Use diamond operator for `new` expressions in generated code (#4045)
* Always use factory methods for `LinkedHashMap` and `LinkedHashSet` when targeting `SequencedSet` and `SequencedMap` (#3990)
* Improve annotation processing performance by removing regex matching from `Type.describe()` (#3991)
* Add support for generics in arrays (#4050)

### Bugs

* Prevent mapper generation from a type with a generic super bound to a type with a generic extends bound (#3994)
* Fix location for Javadoc when generating the distribution zip
* Stop binding a property-level `@Condition` method to an unrelated source parameter when its type matches by accident (#4037)

### Documentation

* Add `SECURITY.md` and `.github/INCIDENT_RESPONSE.md`

### Build

* Test on JDK 25 and 26, drop integration test on JDK 11 (#4039)
* Specify OpenJDK 21 for the Jitpack build (#4042)
* Add CodeQL custom workflow and set build mode for `java-kotlin` to `none`
* Use diamond operator in test code (#4048)
* Upgrade integration tests to JUnit 5 (#4023)
* Update Maven compiler plugin (#3972)
* Upgrade Freemarker to 2.3.34
* Update license plugin
* Enforce import order via Checkstyle `CustomImportOrder` (#4024)
* Enforce spaces inside parentheses for control flow statements via Checkstyle
* Simplify `fail` in `assertCheckstyleRules`
* Remove deprecated `Number` API usage from tests
* Use `StandardCharsets.UTF_8` in tests
* Remove obsolete override of AssertJ version in integration tests
* Let GitHub determine whether or not the released version is the latest
* Simplify parallel-capable registration in `ModifiableURLClassLoader` test util (#4052)
* Improve class loading for tests by replacing `LauncherDiscoveryListener` with `CompilerLauncherInterceptor` (#4051)

### Internal

* Upgrade internal `Visitor6` usages to `Visitor8`
* Refactor `TypeFactory.getTypeParameters` (#4020)
* Simplify boolean logic in `ValueMappingMethod` by removing inversion (#4007)
* Remove unnecessary `keySet()` invocation (#3989)
* Remove unused methods in `Fields` (#4010)
* Add missing self reference in `GeneratedTypeBuilder` (#4009)
* Fix self check in `equals` of `Type` (#3995)
* Remove reflection from `isDefaultMethod` (#4053)
