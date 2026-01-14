# KSP Processor Test Implementation Checklist

**Total Tests: 176 | Existing: 3 | Remaining: 173**

---

## Phase 1: Core Mapping Functionality (P1 - Critical)

### 1.1 Simple Property Mapping
**File**: `simple/SimpleMapperTest.kt` (extend existing)

- [x] `shouldGenerateSimpleMapper` - Basic property mapping *(exists)*
- [ ] `shouldMapPropertiesWithDifferentNames` - `@Mapping(source = "x", target = "y")`
- [ ] `shouldMapNestedPropertyToFlat` - `source = "nested.property"`
- [ ] `shouldMapFlatPropertyToNested` - `target = "nested.property"`
- [ ] `shouldIgnoreProperty` - `@Mapping(ignore = true)`
- [ ] `shouldMapConstantValue` - `@Mapping(constant = "value")`
- [ ] `shouldMapExpressionValue` - `@Mapping(expression = "...")`
- [ ] `shouldMapDefaultValue` - `@Mapping(defaultValue = "...")`
- [ ] `shouldMapMultipleSourcesToOneTarget` - Multiple source parameters

---

### 1.2 Boolean Property Mapping
**File**: `bool/BooleanMappingTest.kt` (extend existing)

- [x] `shouldMapBooleanPropertyWithIsPrefixedGetter` - `isX` getter pattern *(exists)*
- [x] `shouldMapBooleanPropertyWithIsPrefixedGetterToNonBoolean` *(exists)*
- [ ] `shouldMapBooleanToString` - Boolean to "true"/"false" conversion
- [ ] `shouldMapStringToBoolean` - String to boolean parsing
- [ ] `shouldMapBooleanWithHasPrefix` - Kotlin `hasX` style getters
- [ ] `shouldMapBooleanWithCustomAccessor` - Custom boolean accessor patterns

---

### 1.3 Array Mapping
**File**: `array/ArrayMappingTest.kt` (NEW)

- [ ] `shouldCopyArraysInBean` - Primitive and object arrays between bean properties
- [ ] `shouldMapArrayToArray` - Array of objects to array of objects
- [ ] `shouldMapListToArray` - List to array type conversion
- [ ] `shouldMapArrayToList` - Array to List type conversion
- [ ] `shouldMapArrayWithTypeConversion` - int[] to String[] with element conversion
- [ ] `shouldHandleNullArrayWithDefaultStrategy` - Null source with default return strategy
- [ ] `shouldHandleNullArrayWithNullStrategy` - Null source with null return strategy
- [ ] `shouldMapToExistingTargetArray` - Update mapping with pre-existing target

---

### 1.4 Collection Mapping
**File**: `collection/CollectionMappingTest.kt` (NEW)

- [ ] `shouldMapListToList` - Basic List to List mapping
- [ ] `shouldMapSetToSet` - Basic Set to Set mapping
- [ ] `shouldMapMapToMap` - Basic Map to Map mapping
- [ ] `shouldMapListWithElementConversion` - List<Integer> to List<String>
- [ ] `shouldMapSetWithElementConversion` - Set element type conversion
- [ ] `shouldMapMapWithKeyValueConversion` - Map key and value type conversion
- [ ] `shouldReturnCopyNotReference` - Verify list is copied, not referenced
- [ ] `shouldHandleNullCollectionWithDefaultStrategy` - Null collection returns empty
- [ ] `shouldHandleNullCollectionWithNullStrategy` - Null collection returns null
- [ ] `shouldMapListWithoutSetter` - Getter-only collection property
- [ ] `shouldMapToImmutableList` - Target is immutable list type
- [ ] `shouldMapCollectionToCollection` - Generic Collection mapping
- [ ] `shouldMapIterableToIterable` - Iterable type mapping

---

### 1.5 Nested Bean Mapping
**File**: `nestedbeans/NestedBeanMappingTest.kt` (NEW)

- [ ] `shouldMapNestedBeans` - Basic nested object mapping
- [ ] `shouldMapDeeplyNestedBeans` - Multiple levels of nesting
- [ ] `shouldUpdateNestedBeans` - Update existing nested objects
- [ ] `shouldHandleRecursiveStructures` - Self-referencing types (trees)
- [ ] `shouldMapNestedCollections` - Collections within nested beans
- [ ] `shouldAutoGenerateNestedMappingMethods` - Verify forged methods are created
- [ ] `shouldReportErrorForUnmappedNestedProperty` - Error message for unmapped nested

---

### 1.6 Type Conversion
**File**: `conversion/TypeConversionTest.kt` (NEW)

- [ ] `shouldConvertIntToLong` - Primitive widening
- [ ] `shouldConvertLongToInt` - Primitive narrowing
- [ ] `shouldConvertIntToString` - Primitive to String
- [ ] `shouldConvertStringToInt` - String to primitive
- [ ] `shouldConvertDateToString` - Date formatting
- [ ] `shouldConvertStringToDate` - Date parsing
- [ ] `shouldConvertBigDecimalToString` - BigDecimal formatting
- [ ] `shouldConvertEnumToString` - Enum name() conversion
- [ ] `shouldConvertStringToEnum` - Enum valueOf() conversion
- [ ] `shouldConvertWrapperToPrimitive` - Unboxing (Integer to int)
- [ ] `shouldConvertPrimitiveToWrapper` - Boxing (int to Integer)
- [ ] `shouldApplyNumberFormatting` - `@Mapping(numberFormat = "...")`
- [ ] `shouldApplyDateFormatting` - `@Mapping(dateFormat = "...")`

---

### 1.7 Constructor Mapping
**File**: `constructor/ConstructorMappingTest.kt` (NEW)

- [ ] `shouldMapUsingConstructor` - Basic constructor-based target creation
- [ ] `shouldMapWithConstructorAndSetters` - Mixed constructor + setter mapping
- [ ] `shouldSelectPublicConstructor` - Constructor visibility selection
- [ ] `shouldUseDefaultAnnotatedConstructor` - `@Default` constructor selection
- [ ] `shouldMapConstructorWithConstants` - Constant values in constructor params
- [ ] `shouldMapConstructorWithExpressions` - Expression values in constructor params
- [ ] `shouldReportAmbiguousConstructorError` - Error for multiple constructors
- [ ] `shouldMapNestedPropertiesToConstructor` - Nested source to constructor params
- [ ] `shouldMapMultipleSourcesToConstructor` - Multiple source params to constructor

---

### 1.8 Builder Pattern Mapping
**File**: `builder/BuilderMappingTest.kt` (NEW)

- [ ] `shouldMapUsingSimpleBuilder` - Basic builder pattern
- [ ] `shouldMapUsingInnerClassBuilder` - Inner class builder pattern
- [ ] `shouldMapUsingStaticFactoryBuilder` - Builder from static factory method
- [ ] `shouldMapNestedPropertiesWithBuilder` - Nested property flattening with builder
- [ ] `shouldMapToImmutableWithBuilder` - Immutable object via builder
- [ ] `shouldDetectBuilderAutomatically` - Auto-detect builder pattern
- [ ] `shouldReportMissingRequiredBuilderProperty` - Error for unmapped required property
- [ ] `shouldUseLombokBuilder` - Lombok @Builder support (verify compatibility)
- [ ] `shouldUseKotlinDataClassBuilder` - Kotlin data class with builder

---

### 1.9 Inheritance Mapping
**File**: `inheritance/InheritanceMappingTest.kt` (NEW)

- [ ] `shouldMapAttributeFromSuperType` - Inherited property mapping
- [ ] `shouldMapToSubclass` - Target is subclass of declared return
- [ ] `shouldResolveMapperInheritance` - Mapper interface inheritance
- [ ] `shouldInheritMappingConfiguration` - `@InheritConfiguration`
- [ ] `shouldInheritInverseConfiguration` - `@InheritInverseConfiguration`
- [ ] `shouldReportAmbiguousInheritedMethods` - Error for ambiguous inherited methods

---

### 1.10 Generics Mapping
**File**: `generics/GenericsMappingTest.kt` (NEW)

- [ ] `shouldMapGenericTypeParameter` - Generic <T> type handling
- [ ] `shouldMapBoundedTypeParameter` - `<T extends Number>` bounds
- [ ] `shouldMapWildcardType` - `<? extends T>` wildcards
- [ ] `shouldMapNestedGenericTypes` - `List<Map<K, V>>`
- [ ] `shouldInheritFromGenericMapper` - Mapper extends generic base
- [ ] `shouldResolveGenericReturnType` - Generic method return types
- [ ] `shouldResolveGenericSourceType` - Generic method source types

---

## Phase 2: Advanced Features (P2 - High Priority)

### 2.1 Qualifier Selection
**File**: `selection/QualifierSelectionTest.kt` (NEW)

- [ ] `shouldSelectMethodByQualifier` - `@Qualifier` annotation selection
- [ ] `shouldSelectByNamedQualifier` - `@Named("name")` selection
- [ ] `shouldSelectQualifiedFactory` - Factory method with qualifier
- [ ] `shouldCombineClassAndMethodQualifiers` - Multiple qualifier levels
- [ ] `shouldReportNoMatchingQualifierError` - Error when no match found

---

### 2.2 Null Value Handling
**File**: `nullvalue/NullValueMappingTest.kt` (NEW)

- [ ] `shouldReturnNullWhenSourceIsNull` - Default null behavior
- [ ] `shouldReturnDefaultWhenSourceIsNull` - `nullValueMappingStrategy = RETURN_DEFAULT`
- [ ] `shouldApplyNullValuePropertyMapping` - Property-level null strategy
- [ ] `shouldMapExpressionsWithNullSource` - Expression with null source
- [ ] `shouldMapConstantsWithNullSource` - Constant with null source
- [ ] `shouldApplyIterableNullStrategy` - Null collection handling
- [ ] `shouldApplyMapNullStrategy` - Null map handling

---

### 2.3 Callback Methods
**File**: `callbacks/CallbackMethodTest.kt` (NEW)

- [ ] `shouldInvokeBeforeMapping` - `@BeforeMapping` lifecycle
- [ ] `shouldInvokeAfterMapping` - `@AfterMapping` lifecycle
- [ ] `shouldPassTargetToAfterMapping` - `@MappingTarget` in callback
- [ ] `shouldInvokeCallbacksForIterables` - Callbacks for collection mapping
- [ ] `shouldSelectCallbackByQualifier` - Qualified callback selection
- [ ] `shouldInvokeCallbacksForEnums` - Callbacks for enum mapping

---

### 2.4 Update Methods
**File**: `updatemethods/UpdateMethodTest.kt` (NEW)

- [ ] `shouldPreferUpdateMethod` - `@MappingTarget` for updates
- [ ] `shouldClearExistingValuesOnUpdate` - Null source clears target
- [ ] `shouldUseNestedUpdateMethods` - Nested object updates
- [ ] `shouldUpdateCollectionProperties` - Collection property updates
- [ ] `shouldReportErrorForReadOnlyTarget` - Error for getter-only target

---

### 2.5 Factory Methods
**File**: `factories/FactoryMethodTest.kt` (NEW)

- [ ] `shouldUseFactoryMethod` - Basic object factory
- [ ] `shouldUseGenericFactory` - Generic factory method
- [ ] `shouldSelectFactoryByQualifier` - Qualified factory selection
- [ ] `shouldUseCollectionFactory` - Custom collection factory
- [ ] `shouldUseMapFactory` - Custom map factory

---

### 2.6 Decorator Pattern
**File**: `decorator/DecoratorTest.kt` (NEW)

- [ ] `shouldInvokeDecoratorMethods` - Basic decorator pattern
- [ ] `shouldDelegateToDefaultImplementation` - Non-decorated methods delegate
- [ ] `shouldApplyDecoratorToClassMapper` - Class-based mapper decoration
- [ ] `shouldReportInvalidDecoratorType` - Error for wrong decorator type

---

### 2.7 Context Parameters
**File**: `context/ContextParameterTest.kt` (NEW)

- [ ] `shouldPassContextToMappingMethods` - `@Context` parameter passing
- [ ] `shouldResolveCyclesWithContext` - Cycle detection via context
- [ ] `shouldReportDuplicateContextType` - Error for duplicate context type
- [ ] `shouldUseContextInNestedMapping` - Context in auto-generated methods

---

### 2.8 Conditional Mapping
**File**: `conditional/ConditionalMappingTest.kt` (NEW)

- [ ] `shouldApplyConditionalMethod` - `@Condition` with custom check
- [ ] `shouldApplyConditionExpression` - `conditionExpression = "..."`
- [ ] `shouldApplyConditionForCollection` - Conditional for collections
- [ ] `shouldApplySourceParameterCondition` - Condition on source param
- [ ] `shouldReportAmbiguousCondition` - Error for ambiguous conditions

---

### 2.9 Value Mapping (Enums)
**File**: `value/EnumMappingTest.kt` (NEW)

- [ ] `shouldMapEnumToEnum` - Same-name enum mapping
- [ ] `shouldMapEnumWithValueMapping` - `@ValueMapping` explicit mapping
- [ ] `shouldMapEnumToString` - Enum to String conversion
- [ ] `shouldMapStringToEnum` - String to Enum conversion
- [ ] `shouldApplyEnumNameTransformation` - Custom name transformation
- [ ] `shouldThrowForUnmappedEnumValue` - Exception for unknown value
- [ ] `shouldMapManyToOneEnumValue` - Multiple sources to one target
- [ ] `shouldMapEnumToNull` - Enum value mapped to null

---

### 2.10 Subclass Mapping
**File**: `subclassmapping/SubclassMappingTest.kt` (NEW)

- [ ] `shouldMapSubclassToSubclass` - `@SubclassMapping` usage
- [ ] `shouldMapBaseClassFallback` - Base class mapping fallback
- [ ] `shouldResolveSubclassAutomatically` - Automatic subclass detection
- [ ] `shouldReportAmbiguousSubclass` - Error for ambiguous subclasses

---

### 2.11 Stream Mapping
**File**: `stream/StreamMappingTest.kt` (NEW)

- [ ] `shouldMapStreamToStream` - Stream to Stream mapping
- [ ] `shouldMapStreamToList` - Stream to List conversion
- [ ] `shouldMapListToStream` - List to Stream conversion
- [ ] `shouldMapStreamWithElementConversion` - Stream element type conversion

---

### 2.12 Dependency Injection
**File**: `injection/DependencyInjectionTest.kt` (NEW)

- [ ] `shouldGenerateSpringComponent` - `componentModel = "spring"`
- [ ] `shouldGenerateCdiBean` - `componentModel = "cdi"`
- [ ] `shouldGenerateJakartaCdiBean` - `componentModel = "jakarta"`
- [ ] `shouldInjectDependencies` - Inject other mappers
- [ ] `shouldUseConstructorInjection` - `injectionStrategy = CONSTRUCTOR`
- [ ] `shouldUseFieldInjection` - `injectionStrategy = FIELD`

---

## Phase 3: Error Handling & Edge Cases (P1 - Critical)

### 3.1 Compilation Errors
**File**: `erroneous/CompilationErrorTest.kt` (NEW)

- [ ] `shouldReportUnmappedTargetProperty` - Error for unmapped target
- [ ] `shouldReportUnmappedSourceProperty` - Error for unmapped source
- [ ] `shouldReportAmbiguousMappingMethods` - Error for ambiguous methods
- [ ] `shouldReportDuplicateTargetProperty` - Error for duplicate target
- [ ] `shouldReportNonExistentSourceProperty` - Error for unknown source
- [ ] `shouldReportNonExistentTargetProperty` - Error for unknown target
- [ ] `shouldReportIncompatibleTypes` - Error for unmappable types
- [ ] `shouldReportInvalidExpression` - Error for malformed expression
- [ ] `shouldReportInvalidDateFormat` - Error for bad date format
- [ ] `shouldReportCircularDependency` - Error for circular references

---

### 3.2 Warning Messages
**File**: `erroneous/WarningMessageTest.kt` (NEW)

- [ ] `shouldWarnUnmappedTargetProperties` - Warning level for unmapped
- [ ] `shouldIgnoreUnmappedProperties` - Ignore strategy
- [ ] `shouldReportVerboseMessages` - Verbose mode output

---

## Phase 4: Kotlin-Specific Features (P1 - Critical for KSP)

### 4.1 Data Classes
**File**: `kotlin/DataClassMappingTest.kt` (NEW)

- [ ] `shouldMapKotlinDataClass` - Basic data class mapping
- [ ] `shouldMapDataClassWithDefaultValues` - Default parameter values
- [ ] `shouldMapDataClassCopy` - Data class copy() method
- [ ] `shouldMapNullableDataClassProperties` - Nullable properties (T?)

---

### 4.2 Sealed Classes
**File**: `kotlin/SealedClassMappingTest.kt` (NEW)

- [ ] `shouldMapSealedClass` - Sealed class hierarchy mapping
- [ ] `shouldMapSealedClassSubtypes` - Map to specific sealed subtypes

---

### 4.3 Kotlin Nullability
**File**: `kotlin/NullabilityTest.kt` (NEW)

- [ ] `shouldHandleNullableToNonNullable` - `T?` to `T` mapping
- [ ] `shouldHandleNonNullableToNullable` - `T` to `T?` mapping
- [ ] `shouldRespectNullabilityConstraints` - Type-safe null handling

---

### 4.4 Extension Functions
**File**: `kotlin/ExtensionFunctionTest.kt` (NEW)

- [ ] `shouldGenerateAsExtensionFunction` - Extension function mapper style
- [ ] `shouldUseExtensionFunctionMapper` - Extension function invocation

---

### 4.5 Companion Object
**File**: `kotlin/CompanionObjectTest.kt` (NEW)

- [ ] `shouldRecognizeCompanionFactoryMethod` - Companion object as factory
- [ ] `shouldUseCompanionObjectMapper` - Mapper in companion object

---

## Summary

### Phase 1: Core Mapping (P1 - Critical)
| Category | Tests | Status |
|----------|-------|--------|
| Simple Property Mapping | 9 | 1/9 ✅ |
| Boolean Property Mapping | 6 | 2/6 ✅ |
| Array Mapping | 8 | 0/8 |
| Collection Mapping | 13 | 0/13 |
| Nested Bean Mapping | 7 | 0/7 |
| Type Conversion | 13 | 0/13 |
| Constructor Mapping | 9 | 0/9 |
| Builder Pattern | 9 | 0/9 |
| Inheritance Mapping | 6 | 0/6 |
| Generics Mapping | 7 | 0/7 |
| **Subtotal** | **87** | **3/87** |

### Phase 2: Advanced Features (P2 - High)
| Category | Tests | Status |
|----------|-------|--------|
| Qualifier Selection | 5 | 0/5 |
| Null Value Handling | 7 | 0/7 |
| Callback Methods | 6 | 0/6 |
| Update Methods | 5 | 0/5 |
| Factory Methods | 5 | 0/5 |
| Decorator Pattern | 4 | 0/4 |
| Context Parameters | 4 | 0/4 |
| Conditional Mapping | 5 | 0/5 |
| Value Mapping (Enums) | 8 | 0/8 |
| Subclass Mapping | 4 | 0/4 |
| Stream Mapping | 4 | 0/4 |
| Dependency Injection | 6 | 0/6 |
| **Subtotal** | **63** | **0/63** |

### Phase 3: Error Handling (P1 - Critical)
| Category | Tests | Status |
|----------|-------|--------|
| Compilation Errors | 10 | 0/10 |
| Warning Messages | 3 | 0/3 |
| **Subtotal** | **13** | **0/13** |

### Phase 4: Kotlin-Specific (P1 - Critical for KSP)
| Category | Tests | Status |
|----------|-------|--------|
| Data Classes | 4 | 0/4 |
| Sealed Classes | 2 | 0/2 |
| Kotlin Nullability | 3 | 0/3 |
| Extension Functions | 2 | 0/2 |
| Companion Object | 2 | 0/2 |
| **Subtotal** | **13** | **0/13** |

---

## Grand Total

| Phase | Tests | Completed | Percentage |
|-------|-------|-----------|------------|
| Phase 1 | 87 | 3 | 3.4% |
| Phase 2 | 63 | 0 | 0% |
| Phase 3 | 13 | 0 | 0% |
| Phase 4 | 13 | 0 | 0% |
| **Total** | **176** | **3** | **1.7%** |

---

## Quality Gates

- [ ] All Phase 1 (P1) tests implemented and passing
- [ ] All Phase 2 (P2) tests implemented and passing
- [ ] All Phase 3 error handling tests passing
- [ ] All Phase 4 Kotlin-specific tests passing
- [ ] Generated code compiles correctly
- [ ] Error messages match expected diagnostic format
- [ ] No regression from existing tests
- [ ] Test infrastructure enhanced with resource-based tests
- [ ] Multi-file test support added

---

## Test Infrastructure Enhancements

- [ ] Add resource-based test fixture loading
- [ ] Add compilation diagnostics assertions
- [ ] Add generated code inspection utilities
- [ ] Add multi-file test support
- [ ] Add `@KspTest` annotation (similar to `@ProcessorTest`)
- [ ] Add `@WithSources` annotation
- [ ] Add `@ExpectedOutcome` annotation

---

*Checklist generated from ksp-processor-test-plan.md*
*Last updated: 2026-01-14*
