### Features

* Support for Java 21 Sequenced Collections (#3240)
* Native support for `java.util.Optional` mapping (#674) - MapStruct now fully supports Optional as both source and target types:
  - `Optional` to `Optional` - Both source and target wrapped in `Optional`
  - `Optional` to Non-`Optional` - Unwrapping `Optional` values
  - Non-`Optional` to `Optional` - Wrapping values in `Optional`
  - `Optional` properties in beans with automatic presence checks. Note, there is no null check done for `Optional` properties.
* Improved support for Kotlin. Requires use of `org.jetbrains.kotlin:kotlin-metadata-jvm`.
  - Data Classes (#2281, #2577, #3031) - MapStruct now properly handles:
    - Single field data classes
    - Proper primary constructor detection
      - Data classes with multiple constructors
      - Data classes with all default parameters
  - Sealed Classes (#3404) - Subclass exhaustiveness is now checked for Kotlin sealed classes
* Add support for ignoring multiple target properties at once (#3838) - Using new annotation `@Ignored`

### Enhancements

* Add support for locale parameter for numberFormat and dateFormat (#3628)
* Detect Builder without a factory method (#3729) - With this if there is an inner class that ends with `Builder` and has a constructor with parameters, 
it will be treated as a potential builder. 
Builders through static methods on the type have a precedence.
* Add support for custom exception for subclass exhaustive strategy for `@SubclassMapping` mapping (#3821) - Available on `@BeanMapping`, `@Mapper` and `@MappingConfig`.
* Add new `NullValuePropertyMappingStrategy#CLEAR` for clearing Collection and Map properties when updating a bean (#1830)
* Use deterministic order for supporting fields and methods (#3940)
* Support `@AnnotatedWith` on decorators (#3659)
* Behaviour change: Add warning/error for redundant `ignoreUnmappedSourceProperties` entries (#3906)
* Behaviour change: Warning when the target has no target properties (#1140)
* Behaviour change: Initialize `Optional` with `Optional.empty` instead of `null` (#3852)
* Behaviour change: Mark `String` to `Number` as lossy conversion (#3848)

### Bugs

* Improve error message when mapping non-iterable to array (#3786)
* Fix conditional mapping with `@TargetPropertyName` failing for nested update mappings (#3809)
* Resolve duplicate invocation of overloaded lifecycle methods with inheritance (#3849) - It is possible to disable this by using the new compiler option `mapstruct.disableLifecycleOverloadDeduplicateSelector`.
* Support generic `@Context` (#3711)
* Properly apply `NullValuePropertyMappingStrategy.IGNORE` for collections / maps without setters (#3806)
* Properly recognize the type of public generic fields (#3807)
* Fix method in Record is treated as a fluent setter (#3886)
* Ensure `NullValuePropertyMappingStrategy.SET_TO_DEFAULT` initializes empty collection/map when target is null (#3884)
* Fix Compiler error when mapping an object named `Override` (#3905)

### Documentation

* General Improvements
  * Javadoc
  * Typos in comments
  * Small code refactorings

### Build

* Move Windows and MacOS builds outside of the main workflow
* Update release to release using the new Maven Central Portal
* Skip codecov coverage on forks
* Improve testing support for Kotlin

### Behaviour Change

#### Warning when the target has no target properties (#1140)

With this change, if the target bean does not have any target properties, a warning will be shown.
This is like this to avoid potential mistakes by users, where they might think that the target bean has properties, but it does not.

#### Warning for redundant `ignoreUnmappedSourceProperties` entries (#3906)

With this change, if the `ignoreUnmappedSourceProperties` configuration contains properties that are actually mapped, a warning or compiler error will be shown.
The `unmappedSourcePolicy` is used to determine whether a warning, or an error is shown.

#### Initialize `Optional` with `Optional.empty` instead of `null` (#3852)

With this change, if the target `Optional` property is null, it will be initialized with `Optional.empty()` instead of `null`.

#### Mark `String` to `Number` as lossy conversion (#3848)

With this change, if the source `String` property is mapped to a `Number` property, a warning will be shown.
This is similar to what is happening when mapping `long` to `int`, etc.
The `typeConversionPolicy` `ReportingPolicy` is used to determine whether a warning, error or ignore is shown.
