### Features

### Enhancements

* Breaking change:g (#3574) - 
This reverts #2560, because we've decided that `@BeanMapping(ignoreByDefault = true)` should only be applied to target properties and not to source properties. 
Source properties are ignored anyway, the `BeanMapping#unmappedSourcePolicy` should be used to control what should happen with unmapped source policy

### Bugs

### Documentation

### Build

