# KSP Processor Test Coverage Plan

## Executive Summary

This document provides a comprehensive plan for implementing missing tests in the `ksp-processor` module by analyzing the test coverage in the `processor` module and identifying tests that are relevant for KSP (Kotlin Symbol Processing).

### Current State

| Metric | Processor Module | KSP Processor Module |
|--------|------------------|---------------------|
| **Test Classes** | 518 | 2 |
| **Test Categories** | 71+ | 2 |
| **Test Methods** | ~3,000+ | 3 |
| **Coverage (by test methods)** | Comprehensive | ~0.1% (3 of ~3,000+ methods) |

### Test Categories Overview

The processor module contains tests organized in these categories:

| Category | Test Classes | Priority | KSP Relevance |
|----------|-------------|----------|---------------|
| `bugs/` | 244 | P3 | Variable |
| `injectionstrategy/` | 27 | P2 | ✅ High |
| `selection/` | 23 | P1 | ✅ Critical |
| `conversion/` | 21 | P1 | ✅ Critical |
| `builder/` | 14 | P1 | ✅ Critical |
| `collection/` | 13 | P1 | ✅ Critical |
| `constructor/` | 11 | P1 | ✅ Critical |
| `source/` | 11 | P2 | ✅ High |
| `decorator/` | 10 | P2 | ✅ High |
| `value/` | 10 | P1 | ✅ Critical |
| `nestedbeans/` | 9 | P1 | ✅ Critical |
| `java8stream/` | 9 | P2 | ✅ High |
| `erroneous/` | 9 | P1 | ✅ Critical |
| `subclassmapping/` | 6 | P2 | ✅ High |
| `conditional/` | 5 | P2 | ✅ High |
| `imports/` | 5 | P2 | ✅ High |
| `factories/` | 4 | P2 | ✅ High |
| `callbacks/` | 4 | P2 | ✅ High |
| `Other categories` | 50+ | P2-P3 | Variable |

---

## Phase 1: Core Mapping Functionality (P1 - Critical)

### 1.1 Simple Property Mapping ✅ (Partially Exists)

**Current Status**: `SimpleMapperTest.kt` exists with 1 test

**Existing Test**:
- `shouldGenerateSimpleMapper()` - Basic property mapping

**Additional Tests Needed**:

| Test Name | Use Case | Reference |
|-----------|----------|-----------|
| `shouldMapPropertiesWithDifferentNames` | Tests `@Mapping(source = "x", target = "y")` | `simple/` |
| `shouldMapNestedPropertyToFlat` | Tests `source = "nested.property"` | `nestedproperties/` |
| `shouldMapFlatPropertyToNested` | Tests `target = "nested.property"` | `nestedtargetproperties/` |
| `shouldIgnoreProperty` | Tests `@Mapping(ignore = true)` | `ignore/` |
| `shouldMapConstantValue` | Tests `@Mapping(constant = "value")` | `source/constants/` |
| `shouldMapExpressionValue` | Tests `@Mapping(expression = "...")` | `source/expressions/` |
| `shouldMapDefaultValue` | Tests `@Mapping(defaultValue = "...")` | `defaultvalue/` |
| `shouldMapMultipleSourcesToOneTarget` | Tests multiple source parameters | `multisource/` |

---

### 1.2 Boolean Property Mapping ✅ (Partially Exists)

**Current Status**: `BooleanMappingTest.kt` exists with 2 tests

**Existing Tests**:
- `shouldMapBooleanPropertyWithIsPrefixedGetter()`
- (Additional boolean test)

**Additional Tests Needed**:

| Test Name | Use Case | Reference |
|-----------|----------|-----------|
| `shouldMapBooleanToString` | Boolean to "true"/"false" conversion | `bool/` |
| `shouldMapStringToBoolean` | String to boolean parsing | `bool/` |
| `shouldMapBooleanWithHasPrefix` | Kotlin `hasX` style getters | `bool/` |
| `shouldMapBooleanWithCustomAccessor` | Custom boolean accessor patterns | `bool/` |

---

### 1.3 Array Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldCopyArraysInBean` | Primitive and object arrays between bean properties | ✅ |
| `shouldMapArrayToArray` | Array of objects to array of objects | ✅ |
| `shouldMapListToArray` | List to array type conversion | ✅ |
| `shouldMapArrayToList` | Array to List type conversion | ✅ |
| `shouldMapArrayWithTypeConversion` | int[] to String[] with element conversion | ✅ |
| `shouldHandleNullArrayWithDefaultStrategy` | Null source with default return strategy | ✅ |
| `shouldHandleNullArrayWithNullStrategy` | Null source with null return strategy | ✅ |
| `shouldMapToExistingTargetArray` | Update mapping with pre-existing target | ✅ |

---

### 1.4 Collection Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapListToList` | Basic List to List mapping | ✅ |
| `shouldMapSetToSet` | Basic Set to Set mapping | ✅ |
| `shouldMapMapToMap` | Basic Map to Map mapping | ✅ |
| `shouldMapListWithElementConversion` | List<Integer> to List<String> | ✅ |
| `shouldMapSetWithElementConversion` | Set element type conversion | ✅ |
| `shouldMapMapWithKeyValueConversion` | Map key and value type conversion | ✅ |
| `shouldReturnCopyNotReference` | Verify list is copied, not referenced | ✅ |
| `shouldHandleNullCollectionWithDefaultStrategy` | Null collection returns empty | ✅ |
| `shouldHandleNullCollectionWithNullStrategy` | Null collection returns null | ✅ |
| `shouldMapListWithoutSetter` | Getter-only collection property | ✅ |
| `shouldMapToImmutableList` | Target is immutable list type | ✅ |
| `shouldMapCollectionToCollection` | Generic Collection mapping | ✅ |
| `shouldMapIterableToIterable` | Iterable type mapping | ✅ |

---

### 1.5 Nested Bean Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapNestedBeans` | Basic nested object mapping | ✅ |
| `shouldMapDeeplyNestedBeans` | Multiple levels of nesting | ✅ |
| `shouldUpdateNestedBeans` | Update existing nested objects | ✅ |
| `shouldHandleRecursiveStructures` | Self-referencing types (trees) | ✅ |
| `shouldMapNestedCollections` | Collections within nested beans | ✅ |
| `shouldAutoGenerateNestedMappingMethods` | Verify forged methods are created | ✅ |
| `shouldReportErrorForUnmappedNestedProperty` | Error message for unmapped nested | ✅ |

---

### 1.6 Type Conversion (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldConvertIntToLong` | Primitive widening | ✅ |
| `shouldConvertLongToInt` | Primitive narrowing | ✅ |
| `shouldConvertIntToString` | Primitive to String | ✅ |
| `shouldConvertStringToInt` | String to primitive | ✅ |
| `shouldConvertDateToString` | Date formatting | ✅ |
| `shouldConvertStringToDate` | Date parsing | ✅ |
| `shouldConvertBigDecimalToString` | BigDecimal formatting | ✅ |
| `shouldConvertEnumToString` | Enum name() conversion | ✅ |
| `shouldConvertStringToEnum` | Enum valueOf() conversion | ✅ |
| `shouldConvertWrapperToPrimitive` | Unboxing (Integer to int) | ✅ |
| `shouldConvertPrimitiveToWrapper` | Boxing (int to Integer) | ✅ |
| `shouldApplyNumberFormatting` | `@Mapping(numberFormat = "...")` | ✅ |
| `shouldApplyDateFormatting` | `@Mapping(dateFormat = "...")` | ✅ |

---

### 1.7 Constructor Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapUsingConstructor` | Basic constructor-based target creation | ✅ |
| `shouldMapWithConstructorAndSetters` | Mixed constructor + setter mapping | ✅ |
| `shouldSelectPublicConstructor` | Constructor visibility selection | ✅ |
| `shouldUseDefaultAnnotatedConstructor` | `@Default` constructor selection | ✅ |
| `shouldMapConstructorWithConstants` | Constant values in constructor params | ✅ |
| `shouldMapConstructorWithExpressions` | Expression values in constructor params | ✅ |
| `shouldReportAmbiguousConstructorError` | Error for multiple constructors | ✅ |
| `shouldMapNestedPropertiesToConstructor` | Nested source to constructor params | ✅ |
| `shouldMapMultipleSourcestoConstructor` | Multiple source params to constructor | ✅ |

---

### 1.8 Builder Pattern Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapUsingSimpleBuilder` | Basic builder pattern | ✅ |
| `shouldMapUsingInnerClassBuilder` | Inner class builder pattern | ✅ |
| `shouldMapUsingStaticFactoryBuilder` | Builder from static factory method | ✅ |
| `shouldMapNestedPropertiesWithBuilder` | Nested property flattening with builder | ✅ |
| `shouldMapToImmutableWithBuilder` | Immutable object via builder | ✅ |
| `shouldDetectBuilderAutomatically` | Auto-detect builder pattern | ✅ |
| `shouldReportMissingRequiredBuilderProperty` | Error for unmapped required property | ✅ |
| `shouldUseLombokBuilder` | Lombok @Builder support | ⚠️ Verify |
| `shouldUseKotlinDataClassBuilder` | Kotlin data class with builder | ✅ |

---

### 1.9 Inheritance Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapAttributeFromSuperType` | Inherited property mapping | ✅ |
| `shouldMapToSubclass` | Target is subclass of declared return | ✅ |
| `shouldResolveMapperInheritance` | Mapper interface inheritance | ✅ |
| `shouldInheritMappingConfiguration` | `@InheritConfiguration` | ✅ |
| `shouldInheritInverseConfiguration` | `@InheritInverseConfiguration` | ✅ |
| `shouldReportAmbiguousInheritedMethods` | Error for ambiguous inherited methods | ✅ |

---

### 1.10 Generics Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapGenericTypeParameter` | Generic <T> type handling | ✅ |
| `shouldMapBoundedTypeParameter` | `<T extends Number>` bounds | ✅ |
| `shouldMapWildcardType` | `<? extends T>` wildcards | ✅ |
| `shouldMapNestedGenericTypes` | `List<Map<K, V>>` | ✅ |
| `shouldInheritFromGenericMapper` | Mapper extends generic base | ✅ |
| `shouldResolveGenericReturnType` | Generic method return types | ✅ |
| `shouldResolveGenericSourceType` | Generic method source types | ✅ |

---

## Phase 2: Advanced Features (P2 - High Priority)

### 2.1 Qualifier Selection (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldSelectMethodByQualifier` | `@Qualifier` annotation selection | ✅ |
| `shouldSelectByNamedQualifier` | `@Named("name")` selection | ✅ |
| `shouldSelectQualifiedFactory` | Factory method with qualifier | ✅ |
| `shouldCombineClassAndMethodQualifiers` | Multiple qualifier levels | ✅ |
| `shouldReportNoMatchingQualifierError` | Error when no match found | ✅ |

---

### 2.2 Null Value Handling (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldReturnNullWhenSourceIsNull` | Default null behavior | ✅ |
| `shouldReturnDefaultWhenSourceIsNull` | `nullValueMappingStrategy = RETURN_DEFAULT` | ✅ |
| `shouldApplyNullValuePropertyMapping` | Property-level null strategy | ✅ |
| `shouldMapExpressionsWithNullSource` | Expression with null source | ✅ |
| `shouldMapConstantsWithNullSource` | Constant with null source | ✅ |
| `shouldApplyIterableNullStrategy` | Null collection handling | ✅ |
| `shouldApplyMapNullStrategy` | Null map handling | ✅ |

---

### 2.3 Callback Methods (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldInvokeBeforeMapping` | `@BeforeMapping` lifecycle | ✅ |
| `shouldInvokeAfterMapping` | `@AfterMapping` lifecycle | ✅ |
| `shouldPassTargetToAfterMapping` | `@MappingTarget` in callback | ✅ |
| `shouldInvokeCallbacksForIterables` | Callbacks for collection mapping | ✅ |
| `shouldSelectCallbackByQualifier` | Qualified callback selection | ✅ |
| `shouldInvokeCallbacksForEnums` | Callbacks for enum mapping | ✅ |

---

### 2.4 Update Methods (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldPreferUpdateMethod` | `@MappingTarget` for updates | ✅ |
| `shouldClearExistingValuesOnUpdate` | Null source clears target | ✅ |
| `shouldUseNestedUpdateMethods` | Nested object updates | ✅ |
| `shouldUpdateCollectionProperties` | Collection property updates | ✅ |
| `shouldReportErrorForReadOnlyTarget` | Error for getter-only target | ✅ |

---

### 2.5 Factory Methods (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldUseFactoryMethod` | Basic object factory | ✅ |
| `shouldUseGenericFactory` | Generic factory method | ✅ |
| `shouldSelectFactoryByQualifier` | Qualified factory selection | ✅ |
| `shouldUseCollectionFactory` | Custom collection factory | ✅ |
| `shouldUseMapFactory` | Custom map factory | ✅ |

---

### 2.6 Decorator Pattern (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldInvokeDecoratorMethods` | Basic decorator pattern | ✅ |
| `shouldDelegateToDefaultImplementation` | Non-decorated methods delegate | ✅ |
| `shouldApplyDecoratorToClassMapper` | Class-based mapper decoration | ✅ |
| `shouldReportInvalidDecoratorType` | Error for wrong decorator type | ✅ |

---

### 2.7 Context Parameters (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldPassContextToMappingMethods` | `@Context` parameter passing | ✅ |
| `shouldResolveCyclesWithContext` | Cycle detection via context | ✅ |
| `shouldReportDuplicateContextType` | Error for duplicate context type | ✅ |
| `shouldUseContextInNestedMapping` | Context in auto-generated methods | ✅ |

---

### 2.8 Conditional Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldApplyConditionalMethod` | `@Condition` with custom check | ✅ |
| `shouldApplyConditionExpression` | `conditionExpression = "..."` | ✅ |
| `shouldApplyConditionForCollection` | Conditional for collections | ✅ |
| `shouldApplySourceParameterCondition` | Condition on source param | ✅ |
| `shouldReportAmbiguousCondition` | Error for ambiguous conditions | ✅ |

---

### 2.9 Value Mapping (Enums) (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapEnumToEnum` | Same-name enum mapping | ✅ |
| `shouldMapEnumWithValueMapping` | `@ValueMapping` explicit mapping | ✅ |
| `shouldMapEnumToString` | Enum to String conversion | ✅ |
| `shouldMapStringToEnum` | String to Enum conversion | ✅ |
| `shouldApplyEnumNameTransformation` | Custom name transformation | ✅ |
| `shouldThrowForUnmappedEnumValue` | Exception for unknown value | ✅ |
| `shouldMapManyToOneEnumValue` | Multiple sources to one target | ✅ |
| `shouldMapEnumToNull` | Enum value mapped to null | ✅ |

---

### 2.10 Subclass Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapSubclassToSubclass` | `@SubclassMapping` usage | ✅ |
| `shouldMapBaseClassFallback` | Base class mapping fallback | ✅ |
| `shouldResolveSubclassAutomatically` | Automatic subclass detection | ✅ |
| `shouldReportAmbiguousSubclass` | Error for ambiguous subclasses | ✅ |

---

### 2.11 Stream Mapping (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapStreamToStream` | Stream to Stream mapping | ✅ |
| `shouldMapStreamToList` | Stream to List conversion | ✅ |
| `shouldMapListToStream` | List to Stream conversion | ✅ |
| `shouldMapStreamWithElementConversion` | Stream element type conversion | ✅ |

---

### 2.12 Dependency Injection (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldGenerateSpringComponent` | `componentModel = "spring"` | ✅ |
| `shouldGenerateCdiBean` | `componentModel = "cdi"` | ✅ |
| `shouldGenerateJakartaCdiBean` | `componentModel = "jakarta"` | ✅ |
| `shouldInjectDependencies` | Inject other mappers | ✅ |
| `shouldUseConstructorInjection` | `injectionStrategy = CONSTRUCTOR` | ✅ |
| `shouldUseFieldInjection` | `injectionStrategy = FIELD` | ✅ |

---

## Phase 3: Error Handling & Edge Cases (P1 - Critical)

### 3.1 Compilation Errors (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldReportUnmappedTargetProperty` | Error for unmapped target | ✅ |
| `shouldReportUnmappedSourceProperty` | Error for unmapped source | ✅ |
| `shouldReportAmbiguousMappingMethods` | Error for ambiguous methods | ✅ |
| `shouldReportDuplicateTargetProperty` | Error for duplicate target | ✅ |
| `shouldReportNonExistentSourceProperty` | Error for unknown source | ✅ |
| `shouldReportNonExistentTargetProperty` | Error for unknown target | ✅ |
| `shouldReportIncompatibleTypes` | Error for unmappable types | ✅ |
| `shouldReportInvalidExpression` | Error for malformed expression | ✅ |
| `shouldReportInvalidDateFormat` | Error for bad date format | ✅ |
| `shouldReportCircularDependency` | Error for circular references | ✅ |

---

### 3.2 Warning Messages (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldWarnUnmappedTargetProperties` | Warning level for unmapped | ✅ |
| `shouldIgnoreUnmappedProperties` | Ignore strategy | ✅ |
| `shouldReportVerboseMessages` | Verbose mode output | ✅ |

---

## Phase 4: Kotlin-Specific Features (P1 - Critical for KSP)

### 4.1 Data Classes (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapKotlinDataClass` | Basic data class mapping | ✅ |
| `shouldMapDataClassWithDefaultValues` | Default parameter values | ✅ |
| `shouldMapDataClassCopy` | Data class copy() method | ✅ |
| `shouldMapNullableDataClassProperties` | Nullable properties (T?) | ✅ |

---

### 4.2 Sealed Classes (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldMapSealedClass` | Sealed class hierarchy mapping | ✅ |
| `shouldMapSealedClassSubtypes` | Map to specific sealed subtypes | ✅ |

---

### 4.3 Kotlin Nullability (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldHandleNullableToNonNullable` | `T?` to `T` mapping | ✅ |
| `shouldHandleNonNullableToNullable` | `T` to `T?` mapping | ✅ |
| `shouldRespectNullabilityConstraints` | Type-safe null handling | ✅ |

---

### 4.4 Extension Functions (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldGenerateAsExtensionFunction` | Extension function mapper style | ✅ |
| `shouldUseExtensionFunctionMapper` | Extension function invocation | ✅ |

---

### 4.5 Companion Object (NEW)

**Tests Needed**:

| Test Name | Use Case | KSP Relevant |
|-----------|----------|--------------|
| `shouldRecognizeCompanionFactoryMethod` | Companion object as factory | ✅ |
| `shouldUseCompanionObjectMapper` | Mapper in companion object | ✅ |

---

## Implementation Priority Matrix

### P1 - Must Have (Foundation)
1. Simple property mapping (extend existing)
2. Array mapping
3. Collection mapping
4. Type conversion
5. Constructor mapping
6. Builder pattern
7. Nested bean mapping
8. Compilation errors
9. Kotlin data classes
10. Kotlin nullability

### P2 - Should Have (Features)
1. Inheritance mapping
2. Generics mapping
3. Qualifier selection
4. Null value handling
5. Callback methods
6. Update methods
7. Factory methods
8. Value mapping (enums)
9. Dependency injection
10. Stream mapping

### P3 - Nice to Have (Advanced)
1. Decorator pattern
2. Context parameters
3. Conditional mapping
4. Subclass mapping
5. Sealed classes
6. Extension functions
7. Companion objects
8. Bug regression tests

---

## Test Infrastructure Enhancements

### Current Test DSL

```kotlin
// Current pattern in PluginTestDsl.kt
pluginTest("""
    @Mapper
    interface MyMapper {
        fun map(source: Source): Target
    }
    // ... inline test code
""")
```

### Recommended Enhancements

1. **Resource-based tests**: Load test fixtures from files
2. **Compilation diagnostics assertions**: Assert on specific error messages
3. **Generated code inspection**: Access to generated implementation code
4. **Multi-file tests**: Support for complex scenarios with multiple sources
5. **Annotation-based test definition**: `@KspTest`, `@WithSources`, `@ExpectedOutcome`

---

## Metrics and Goals

### Coverage Targets

| Phase | Test Classes | Test Methods | Target Date |
|-------|-------------|--------------|-------------|
| Phase 1 | 10 | 80+ | - |
| Phase 2 | 12 | 60+ | - |
| Phase 3 | 3 | 25+ | - |
| Phase 4 | 5 | 20+ | - |
| **Total** | **30** | **176** | - |

### Quality Gates

- [ ] All P1 tests implemented and passing
- [ ] All P2 tests implemented and passing
- [ ] Generated code compiles correctly
- [ ] Error messages match expected diagnostic format
- [ ] No regression from existing tests

---

## Appendix A: Processor Test Categories Reference

Complete list of processor module test directories:

```
abstractclass (2 tests) - Abstract mapper classes
accessibility (2 tests) - Property accessibility
additionalsupportedoptions (1 test) - Custom processor options
annotatewith (3 tests) - Custom annotation decoration
array (1 test) - Array type mapping
bool (1 test) - Boolean property mapping
bugs (244 tests) - Issue regression tests
builder (14 tests) - Builder pattern support
builtin (3 tests) - Built-in type conversions
callbacks (4 tests) - Lifecycle callbacks
collection (13 tests) - Collection type mapping
complex (1 test) - Complex mapping scenarios
conditional (5 tests) - Conditional mapping
constructor (11 tests) - Constructor-based mapping
context (3 tests) - Context parameter handling
conversion (21 tests) - Type conversion
decorator (10 tests) - Decorator pattern
defaultcomponentmodel (1 test) - Default component model
defaultvalue (1 test) - Default values
dependency (2 tests) - Dependency handling
destination (2 tests) - Target property handling
emptytarget (1 test) - Empty target mapping
erroneous (9 tests) - Compilation error scenarios
exceptions (1 test) - Exception handling
factories (4 tests) - Factory method support
fields (1 test) - Field access mapping
frommap (1 test) - Map source mapping
gem (2 tests) - Annotation gems
generics (2 tests) - Generic type handling
ignore (3 tests) - Property ignoring
ignorebydefaultsource (1 test) - Default source ignoring
ignored (1 test) - Ignored mappings
ignoreunmapped (1 test) - Unmapped property ignoring
imports (5 tests) - Import generation
inheritance (3 tests) - Inheritance mapping
inheritedmappingmethod (1 test) - Inherited mapping methods
inheritfromconfig (2 tests) - Config inheritance
injectionstrategy (27 tests) - DI strategies
java8stream (9 tests) - Stream mapping
javadoc (1 test) - Javadoc generation
mapperconfig (1 test) - Mapper configuration
mappingcomposition (1 test) - Mapping composition
mappingcontrol (1 test) - Mapping control
missingignoredsource (1 test) - Missing ignored source
multisource (1 test) - Multiple source parameters
namesuggestion (1 test) - Property name suggestions
naming (2 tests) - Naming strategies
nestedbeans (9 tests) - Nested bean mapping
nestedmethodcall (1 test) - Nested method calls
nestedproperties (1 test) - Nested properties
nestedsource (2 tests) - Nested source mapping
nestedsourceproperties (2 tests) - Nested source properties
nestedtargetproperties (1 test) - Nested target properties
nonvoidsetter (1 test) - Non-void setter methods
nullcheck (2 tests) - Null checking
nullvaluemapping (3 tests) - Null value strategies
nullvaluepropertymapping (1 test) - Null property strategies
oneway (1 test) - One-way mapping
optional (1 test) - Optional type handling
references (3 tests) - Reference handling
reverse (1 test) - Reverse mapping
selection (23 tests) - Method selection
source (11 tests) - Source handling
subclassmapping (6 tests) - Subclass mapping
superbuilder (1 test) - Lombok super builder
targetthis (1 test) - Target this reference
template (1 test) - Template mapping
unmappedsource (2 tests) - Unmapped source properties
unmappedtarget (1 test) - Unmapped target properties
updatemethods (3 tests) - Update methods
value (10 tests) - Value/enum mapping
verbose (1 test) - Verbose output
versioninfo (1 test) - Version information
```

---

## Appendix B: Test Method Examples

### Example: Array Mapping Test

```kotlin
@Test
fun shouldMapArrayToArray() = pluginTest("""
    data class Source(val values: Array<String>)
    data class Target(val values: Array<String>)

    @Mapper
    interface ArrayMapper {
        fun map(source: Source): Target
    }

    fun test() {
        val mapper = ArrayMapperImpl()
        val source = Source(arrayOf("a", "b", "c"))
        val target = mapper.map(source)
        assert(target.values.contentEquals(arrayOf("a", "b", "c")))
    }
""")
```

### Example: Collection Mapping Test

```kotlin
@Test
fun shouldMapListToList() = pluginTest("""
    data class Source(val items: List<String>)
    data class Target(val items: List<String>)

    @Mapper
    interface CollectionMapper {
        fun map(source: Source): Target
    }

    fun test() {
        val mapper = CollectionMapperImpl()
        val source = Source(listOf("x", "y", "z"))
        val target = mapper.map(source)
        assert(target.items == listOf("x", "y", "z"))
    }
""")
```

### Example: Compilation Error Test

```kotlin
@Test
fun shouldReportUnmappedTargetProperty() = pluginTest("""
    data class Source(val name: String)
    data class Target(val name: String, val age: Int)

    @Mapper
    interface ErrorMapper {
        fun map(source: Source): Target
    }
""").failsWith { message ->
    message.contains("Unmapped target property: \"age\"")
}
```

---

*Document generated by analyzing MapStruct processor test coverage*
*Last updated: 2026-01-14*
