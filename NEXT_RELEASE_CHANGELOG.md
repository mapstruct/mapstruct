### Enhancements

* Use Java `LinkedHashSet` and `LinkedHashMap` new factory method with known capacity when on Java 19 or later (#3113)

### Bugs

* Inverse Inheritance Strategy not working for ignored mappings only with target (#3652)
* Inconsistent ambiguous mapping method error when using `SubclassMapping`: generic vs raw types (#3668)
* Fix regression when using `InheritInverseConfiguration` with nested target properties and reversing `target = "."` (#3670)
* Deep mapping with multiple mappings broken in 1.6.0 (#3667)
* Two different constants are ignored in 1.6.0 (#3673)
* Inconsistent ambiguous mapping method error: generic vs raw types in 1.6.0 (#3668)
* Fix cross module records with interfaces not recognizing accessors (#3661)
* `@AfterMapping` methods are called twice when using target with builder (#3678)
* Compile error when using `@AfterMapping` method with Builder and TargetObject (#3703)
