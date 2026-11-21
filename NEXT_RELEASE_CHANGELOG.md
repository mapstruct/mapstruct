### Features

* Use nullability consistently across the generated code (#4131). Nullability is now part of the MapStruct model, which extends the JSpecify support to:
  - Add JSpecify nullness annotations (`@NonNull`, `@Nullable`, `@NullMarked`, `@NullUnmarked`) to the generated code when JSpecify is on the compile classpath (#4036, #4076)
  - Add a null check before passing a potentially `null` value to a method with a `@NonNull` parameter, including user methods, built-in methods and chained (2 step) method calls (#4077, #4081, #4086, #4106)
  - Support the nullability of array component types (`String @Nullable []` vs `@Nullable String[]`)
  - Take the nullability of auxiliary (e.g. `@Context`) parameters into account
  - Value mappings: skip the null check for a `@NonNull` source, report an error when a method with a `@NonNull` return type could return `null`, and report a warning when `@ValueMapping(source = MappingConstants.NULL)` can never be applied because the source is `@NonNull`

### Enhancements

### Bugs

### Documentation

* Document the nullability rules for method calls, container mappings and value mappings, and the JSpecify annotations in the generated code (#4131)

### Build

### Behaviour Change

#### JSpecify annotations in the generated code (#4036, #4076)

When JSpecify is on the compile classpath, MapStruct adds JSpecify nullness annotations to the generated code, e.g. `@NonNull` on the parameters of generated helper and built-in methods.
This also applies to mappers that do not use JSpecify themselves, for example when JSpecify is on the classpath transitively through Guava or Spring 7.
Use the `mapstruct.disableJSpecify` processor option to disable this.

#### No NPE when a method returns `null` for a primitive target (#4131)

When a method returns a wrapper type (e.g. `Integer`) and the result is used for a primitive target (e.g. `long`), the result is now null-checked.
Previously, a `null` result resulted in a `NullPointerException`.
Now the target is treated like for any other `null` source: the setter is not called (an update method keeps the existing value) unless the `NullValuePropertyMappingStrategy` says otherwise.

#### Conversions are not applied to a `null` result of a method (#4131)

When a method is combined with a conversion (e.g. a method returning `Integer` and the `Integer` to `String` conversion), the conversion is only applied when the method returns a non-null value.
MapStruct generates a private helper method for this.
Previously, the conversion was applied to `null` as well, e.g. `String.valueOf( null )` resulted in `"null"`, and conversions like `new BigDecimal( null )` resulted in a `NullPointerException`.

#### `null` elements in iterables, arrays, streams and maps (#4131)

When mapping the elements of an iterable, array, stream or map requires a conversion or a method that does not accept `null`, a `null` element is now mapped to `null`, instead of being passed to the conversion or the method.
The size of the collection and the entries of the map stay the same, e.g. mapping `[1, null, 3]` from `List<Integer>` to `List<String>` results in `["1", null, "3"]`.
Previously, the conversion was applied to the `null` element, which resulted in `"null"` for `String.valueOf`, or in an exception, e.g. a `NumberFormatException` for `Integer.parseInt( null )` or a `NullPointerException` for the built-in `Calendar` to `ZonedDateTime` conversion.
For maps, this applies to both keys and values.
Only when a stream is mapped to an array whose component type is known to be `@NonNull` (JSpecify `@NonNull String[]`), `null` elements are filtered out.
