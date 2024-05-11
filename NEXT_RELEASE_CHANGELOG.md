### Features

* Support conditional mapping for source parameters (#2610, #3459, #3270)
* Add `@SourcePropertyName` to handle a property name of the source object (#3323) - Currently only applicable for `@Condition` methods

### Enhancements

* Improve error message for mapping to `target = "."` using expression (#3485)
* Improve error messages for auto generated mappings (#2788)
* Remove unnecessary casts to long (#3400)

### Bugs

* `@Condition` cannot be used only with `@Context` parameters (#3561)
* `@Condition` treated as ambiguous mapping for methods returning Boolean/boolean (#3565)
* Subclass mapping warns about unmapped property that is mapped in referenced mapper (#3360)
* Interface inherited build method is not found (#3463)
* Bean with getter returning Stream is treating the Stream as an alternative setter (#3462)
* Using `Mapping#expression` and `Mapping#conditionalQualifiedBy(Name)` should lead to compile error (#3413)
* Defined mappings for subclass mappings with runtime exception subclass exhaustive strategy not working if result type is abstract class (#3331)

### Documentation

* Clarify that `Mapping#ignoreByDefault` is inherited in nested mappings in documentation (#3577)

### Build

* Improve tests to show that Lombok `@SuperBuilder` is supported (#3524)
* Add Java 21 CI matrix build (#3473)
