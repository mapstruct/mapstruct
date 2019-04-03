/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.tools.Diagnostic;

/**
 * A message used in warnings/errors raised by the annotation processor.
 *
 * @author Sjaak Derksen
 */
public enum Message {

    // CHECKSTYLE:OFF
    PROCESSING_NOTE( "processing: %s.", Diagnostic.Kind.NOTE ),
    CONFIG_NOTE( "applying mapper configuration: %s.", Diagnostic.Kind.NOTE ),

    BEANMAPPING_CREATE_NOTE( "creating bean mapping method implementation for %s.", Diagnostic.Kind.NOTE ),
    BEANMAPPING_NO_ELEMENTS( "'nullValueMappingStrategy', 'nullValuePropertyMappingStrategy', 'resultType' and 'qualifiedBy' are undefined in @BeanMapping, define at least one of them." ),
    BEANMAPPING_NOT_ASSIGNABLE( "%s not assignable to: %s." ),
    BEANMAPPING_ABSTRACT( "The result type %s may not be an abstract class nor interface." ),
    BEANMAPPING_UNKNOWN_PROPERTY_IN_RESULTTYPE( "Unknown property \"%s\" in result type %s. Did you mean \"%s\"?" ),
    BEANMAPPING_PROPERTY_HAS_NO_WRITE_ACCESSOR_IN_RESULTTYPE( "Property \"%s\" has no write accessor in %s." ),
    BEANMAPPING_SEVERAL_POSSIBLE_SOURCES( "Several possible source properties for target property \"%s\"." ),
    BEANMAPPING_SEVERAL_POSSIBLE_TARGET_ACCESSORS( "Found several matching getters for property \"%s\"." ),
    BEANMAPPING_UNMAPPED_TARGETS_WARNING( "Unmapped target %s.", Diagnostic.Kind.WARNING ),
    BEANMAPPING_UNMAPPED_TARGETS_ERROR( "Unmapped target %s." ),
    BEANMAPPING_UNMAPPED_FORGED_TARGETS_WARNING( "Unmapped target %s. Mapping from %s to %s.", Diagnostic.Kind.WARNING ),
    BEANMAPPING_UNMAPPED_FORGED_TARGETS_ERROR( "Unmapped target %s. Mapping from %s to %s." ),
    BEANMAPPING_UNMAPPED_SOURCES_WARNING( "Unmapped source %s.", Diagnostic.Kind.WARNING ),
    BEANMAPPING_UNMAPPED_SOURCES_ERROR( "Unmapped source %s." ),
    BEANMAPPING_CYCLE_BETWEEN_PROPERTIES( "Cycle(s) between properties given via dependsOn(): %s." ),
    BEANMAPPING_UNKNOWN_PROPERTY_IN_DEPENDS_ON( "\"%s\" is no property of the method return type." ),

    PROPERTYMAPPING_MAPPING_NOTE( "mapping property: %s to: %s.", Diagnostic.Kind.NOTE ),
    PROPERTYMAPPING_CREATE_NOTE( "creating property mapping: %s.", Diagnostic.Kind.NOTE ),
    PROPERTYMAPPING_SELECT_NOTE( "selecting property mapping: %s.", Diagnostic.Kind.NOTE ),
    PROPERTYMAPPING_MAPPING_NOT_FOUND( "Can't map %s to \"%s %s\". Consider to declare/implement a mapping method: \"%s map(%s value)\"." ),
    PROPERTYMAPPING_FORGED_MAPPING_WITH_HISTORY_NOT_FOUND( "No target bean properties found: can't map %s to \"%s %s\". Consider to declare/implement a mapping method: \"%s map(%s value)\"." ),
    PROPERTYMAPPING_FORGED_MAPPING_NOT_FOUND( "No target bean properties found: can't map %s to %s. Consider to implement a mapping method: \"%s map(%s value)\"." ),
    PROPERTYMAPPING_DUPLICATE_TARGETS( "Target property \"%s\" must not be mapped more than once." ),
    PROPERTYMAPPING_EMPTY_TARGET( "Target must not be empty in @Mapping." ),
    PROPERTYMAPPING_SOURCE_AND_CONSTANT_BOTH_DEFINED( "Source and constant are both defined in @Mapping, either define a source or a constant." ),
    PROPERTYMAPPING_SOURCE_AND_IGNORE_BOTH_DEFINED( "Source and ignore are both defined in @Mapping, make explicit in reverse mapping when the intent is to ignore the reverse mapping." ),
    PROPERTYMAPPING_SOURCE_AND_EXPRESSION_BOTH_DEFINED( "Source and expression are both defined in @Mapping, either define a source or an expression." ),
    PROPERTYMAPPING_EXPRESSION_AND_CONSTANT_BOTH_DEFINED( "Expression and constant are both defined in @Mapping, either define an expression or a constant." ),
    PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_VALUE_BOTH_DEFINED( "Expression and default value are both defined in @Mapping, either define a defaultValue or an expression." ),
    PROPERTYMAPPING_CONSTANT_AND_DEFAULT_VALUE_BOTH_DEFINED( "Constant and default value are both defined in @Mapping, either define a defaultValue or a constant." ),
    PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_EXPRESSION_BOTH_DEFINED( "Expression and default expression are both defined in @Mapping, either define an expression or a default expression." ),
    PROPERTYMAPPING_CONSTANT_AND_DEFAULT_EXPRESSION_BOTH_DEFINED( "Constant and default expression are both defined in @Mapping, either define a constant or a default expression." ),
    PROPERTYMAPPING_DEFAULT_VALUE_AND_DEFAULT_EXPRESSION_BOTH_DEFINED( "Default value and default expression are both defined in @Mapping, either define a default value or a default expression." ),
    PROPERTYMAPPING_DEFAULT_VALUE_AND_NVPMS( "Default value and nullValuePropertyMappingStrategy are both defined in @Mapping, either define a defaultValue or an nullValuePropertyMappingStrategy." ),
    PROPERTYMAPPING_EXPRESSION_VALUE_AND_NVPMS( "Expression and nullValuePropertyMappingStrategy are both defined in @Mapping, either define an expression or an nullValuePropertyMappingStrategy." ),
    PROPERTYMAPPING_CONSTANT_VALUE_AND_NVPMS( "Constant and nullValuePropertyMappingStrategy are both defined in @Mapping, either define a constant or an nullValuePropertyMappingStrategy." ),
    PROPERTYMAPPING_DEFAULT_EXPERSSION_AND_NVPMS( "DefaultExpression and nullValuePropertyMappingStrategy are both defined in @Mapping, either define a defaultExpression or an nullValuePropertyMappingStrategy." ),
    PROPERTYMAPPING_IGNORE_AND_NVPMS( "Ignore and nullValuePropertyMappingStrategy are both defined in @Mapping, either define ignore or an nullValuePropertyMappingStrategy." ),
    PROPERTYMAPPING_INVALID_EXPRESSION( "Value for expression must be given in the form \"java(<EXPRESSION>)\"." ),
    PROPERTYMAPPING_INVALID_DEFAULT_EXPRESSION( "Value for default expression must be given in the form \"java(<EXPRESSION>)\"." ),
    PROPERTYMAPPING_INVALID_PARAMETER_NAME( "Method has no source parameter named \"%s\". Method source parameters are: \"%s\"." ),
    PROPERTYMAPPING_NO_PROPERTY_IN_PARAMETER( "The type of parameter \"%s\" has no property named \"%s\"." ),
    PROPERTYMAPPING_INVALID_PROPERTY_NAME( "No property named \"%s\" exists in source parameter(s). Did you mean \"%s\"?" ),
    PROPERTYMAPPING_NO_PRESENCE_CHECKER_FOR_SOURCE_TYPE( "Using custom source value presence checking strategy, but no presence checker found for %s in source type." ),
    PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE( "No read accessor found for property \"%s\" in target type." ),
    PROPERTYMAPPING_NO_WRITE_ACCESSOR_FOR_TARGET_TYPE( "No write accessor found for property \"%s\" in target type." ),
    PROPERTYMAPPING_WHITESPACE_TRIMMED( "The property named \"%s\" has whitespaces, using trimmed property \"%s\" instead.", Diagnostic.Kind.WARNING ),

    CONVERSION_LOSSY_WARNING( "%s has a possibly lossy conversion from %s to %s.", Diagnostic.Kind.WARNING ),
    CONVERSION_LOSSY_ERROR( "Can't map %s. It has a possibly lossy conversion from %s to %s." ),

    CONSTANTMAPPING_MAPPING_NOT_FOUND( "Can't map \"%s %s\" to \"%s %s\"." ),
    CONSTANTMAPPING_MAPPING_NOT_FOUND_WITH_DETAILS( "Can't map \"%s %s\" to \"%s %s\". Reason: %s." ),
    CONSTANTMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE( "No read accessor found for property \"%s\" in target type." ),
    CONSTANTMAPPING_NON_EXISTING_CONSTANT( "Constant %s doesn't exist in enum type %s for property \"%s\"." ),

    MAPMAPPING_CREATE_NOTE( "creating map mapping method implementation for %s.", Diagnostic.Kind.NOTE ),
    MAPMAPPING_KEY_MAPPING_NOT_FOUND( "No implementation can be generated for this method. Found no method nor implicit conversion for mapping source key type to target key type." ),
    MAPMAPPING_VALUE_MAPPING_NOT_FOUND( "No implementation can be generated for this method. Found no method nor implicit conversion for mapping source value type to target value type." ),
    MAPMAPPING_NO_ELEMENTS( "'nullValueMappingStrategy', 'keyDateFormat', 'keyQualifiedBy', 'keyTargetType', 'valueDateFormat', 'valueQualfiedBy' and 'valueTargetType' are all undefined in @MapMapping, define at least one of them." ),
    MAPMAPPING_SELECT_KEY_NOTE( "selecting key mapping: %s.", Diagnostic.Kind.NOTE ),
    MAPMAPPING_SELECT_VALUE_NOTE( "selecting value mapping: %s.", Diagnostic.Kind.NOTE ),
    MAPMAPPING_CREATE_KEY_NOTE( "creating key mapping: %s.", Diagnostic.Kind.NOTE ),
    MAPMAPPING_CREATE_VALUE_NOTE( "creating value mapping: %s.", Diagnostic.Kind.NOTE ),

    STREAMMAPPING_CREATE_NOTE( "creating stream mapping method implementation for %s.", Diagnostic.Kind.NOTE ),
    ITERABLEMAPPING_CREATE_NOTE( "creating iterable mapping method implementation for %s.", Diagnostic.Kind.NOTE ),
    ITERABLEMAPPING_SELECT_ELEMENT_NOTE( "selecting element mapping: %s.", Diagnostic.Kind.NOTE ),
    ITERABLEMAPPING_CREATE_ELEMENT_NOTE( "creating element mapping: %s.", Diagnostic.Kind.NOTE ),
    ITERABLEMAPPING_MAPPING_NOT_FOUND( "No implementation can be generated for this method. Found no method nor implicit conversion for mapping source element type into target element type." ),
    ITERABLEMAPPING_NO_ELEMENTS( "'nullValueMappingStrategy','dateformat', 'qualifiedBy' and 'elementTargetType' are undefined in @IterableMapping, define at least one of them." ),

    ENUMMAPPING_MULTIPLE_SOURCES( "One enum constant must not be mapped to more than one target constant, but constant %s is mapped to %s." ),
    ENUMMAPPING_UNDEFINED_SOURCE( "A source constant must be specified for mappings of an enum mapping method." ),
    ENUMMAPPING_NON_EXISTING_CONSTANT( "Constant %s doesn't exist in enum type %s." ),
    ENUMMAPPING_UNDEFINED_TARGET( "A target constant must be specified for mappings of an enum mapping method." ),
    ENUMMAPPING_UNMAPPED_SOURCES( "The following constants from the source enum have no corresponding constant in the target enum and must be be mapped via adding additional mappings: %s." ),
    ENUMMAPPING_DEPRECATED( "Mapping of Enums via @Mapping is going to be removed in future versions of MapStruct. Please use @ValueMapping instead!", Diagnostic.Kind.WARNING ),

    LIFECYCLEMETHOD_AMBIGUOUS_PARAMETERS( "Lifecycle method has multiple matching parameters (e. g. same type), in this case please ensure to name the parameters in the lifecycle and mapping method identical. This lifecycle method will not be used for the mapping method '%s'.", Diagnostic.Kind.WARNING),

    DECORATOR_NO_SUBTYPE( "Specified decorator type is no subtype of the annotated mapper type." ),
    DECORATOR_CONSTRUCTOR( "Specified decorator type has no default constructor nor a constructor with a single parameter accepting the decorated mapper type." ),

    GENERAL_NO_IMPLEMENTATION( "No implementation type is registered for return type %s." ),
    GENERAL_ABSTRACT_RETURN_TYPE( "The return type %s is an abstract class or interface. Provide a non abstract / non interface result type or a factory method." ),
    GENERAL_AMBIGIOUS_MAPPING_METHOD( "Ambiguous mapping methods found for mapping %s to %s: %s." ),
    GENERAL_AMBIGIOUS_FACTORY_METHOD( "Ambiguous factory methods found for creating %s: %s." ),
    GENERAL_UNSUPPORTED_DATE_FORMAT_CHECK( "No dateFormat check is supported for types %s, %s" ),
    GENERAL_VALID_DATE( "Given date format \"%s\" is valid.", Diagnostic.Kind.NOTE ),
    GENERAL_INVALID_DATE( "Given date format \"%s\" is invalid. Message: \"%s\"." ),
    GENERAL_JODA_NOT_ON_CLASSPATH( "Cannot validate Joda dateformat, no Joda on classpath. Consider adding Joda to the annotation processorpath.", Diagnostic.Kind.WARNING ),
    GENERAL_NOT_ALL_FORGED_CREATED( "Internal Error in creation of Forged Methods, it was expected all Forged Methods to finished with creation, but %s did not" ),
    GENERAL_NO_SUITABLE_CONSTRUCTOR( "%s does not have an accessible parameterless constructor." ),

    BUILDER_MORE_THAN_ONE_BUILDER_CREATION_METHOD( "More than one builder creation method for \"%s\". Found methods: \"%s\". Builder will not be used. Consider implementing a custom BuilderProvider SPI.", Diagnostic.Kind.WARNING ),
    BUILDER_NO_BUILD_METHOD_FOUND("No build method \"%s\" found in \"%s\" for \"%s\". Found methods: \"%s\".", Diagnostic.Kind.ERROR ),
    BUILDER_NO_BUILD_METHOD_FOUND_DEFAULT("No build method \"%s\" found in \"%s\" for \"%s\". Found methods: \"%s\". Consider to add @Builder in order to select the correct build method.", Diagnostic.Kind.ERROR ),

    RETRIEVAL_NO_INPUT_ARGS( "Can't generate mapping method with no input arguments." ),
    RETRIEVAL_DUPLICATE_MAPPING_TARGETS( "Can't generate mapping method with more than one @MappingTarget parameter." ),
    RETRIEVAL_VOID_MAPPING_METHOD( "Can't generate mapping method with return type void." ),
    RETRIEVAL_NON_ASSIGNABLE_RESULTTYPE( "The result type is not assignable to the the return type." ),
    RETRIEVAL_ITERABLE_TO_NON_ITERABLE( "Can't generate mapping method from iterable type to non-iterable type." ),
    RETRIEVAL_MAPPING_HAS_TARGET_TYPE_PARAMETER( "Can't generate mapping method that has a parameter annotated with @TargetType." ),
    RETRIEVAL_NON_ITERABLE_TO_ITERABLE( "Can't generate mapping method from non-iterable type to iterable type." ),
    RETRIEVAL_PRIMITIVE_PARAMETER( "Can't generate mapping method with primitive parameter type." ),
    RETRIEVAL_PRIMITIVE_RETURN( "Can't generate mapping method with primitive return type." ),
    RETRIEVAL_ENUM_TO_NON_ENUM( "Can't generate mapping method from enum type to non-enum type." ),
    RETRIEVAL_NON_ENUM_TO_ENUM( "Can't generate mapping method from non-enum type to enum type." ),
    RETRIEVAL_TYPE_VAR_SOURCE( "Can't generate mapping method for a generic type variable source." ),
    RETRIEVAL_TYPE_VAR_RESULT( "Can't generate mapping method for a generic type variable target." ),
    RETRIEVAL_WILDCARD_SUPER_BOUND_SOURCE( "Can't generate mapping method for a wildcard super bound source." ),
    RETRIEVAL_WILDCARD_EXTENDS_BOUND_RESULT( "Can't generate mapping method for a wildcard extends bound result." ),
    RETRIEVAL_CONTEXT_PARAMS_WITH_SAME_TYPE( "The types of @Context parameters must be unique." ),

    INHERITINVERSECONFIGURATION_DUPLICATES( "Several matching inverse methods exist: %s(). Specify a name explicitly." ),
    INHERITINVERSECONFIGURATION_INVALID_NAME( "None of the candidates %s() matches given name: \"%s\"." ),
    INHERITINVERSECONFIGURATION_DUPLICATE_MATCHES( "Given name \"%s\" matches several candidate methods: %s." ),
    INHERITINVERSECONFIGURATION_NO_NAME_MATCH( "Given name \"%s\" does not match the only candidate. Did you mean: \"%s\"." ),
    INHERITCONFIGURATION_DUPLICATES( "Several matching methods exist: %s(). Specify a name explicitly." ),
    INHERITCONFIGURATION_INVALIDNAME( "None of the candidates %s() matches given name: \"%s\"." ),
    INHERITCONFIGURATION_DUPLICATE_MATCHES( "Given name \"%s\" matches several candidate methods: %s." ),
    INHERITCONFIGURATION_NO_NAME_MATCH( "Given name \"%s\" does not match the only candidate. Did you mean: \"%s\"." ),
    INHERITCONFIGURATION_MULTIPLE_PROTOTYPE_METHODS_MATCH( "More than one configuration prototype method is applicable. Use @InheritConfiguration to select one of them explicitly: %s." ),
    INHERITINVERSECONFIGURATION_MULTIPLE_PROTOTYPE_METHODS_MATCH( "More than one configuration prototype method is applicable. Use @InheritInverseConfiguration to select one of them explicitly: %s." ),
    INHERITCONFIGURATION_CYCLE( "Cycle detected while evaluating inherited configurations. Inheritance path: %s" ),

    VALUEMAPPING_CREATE_NOTE( "creating value mapping method implementation for %s.", Diagnostic.Kind.NOTE ),
    VALUEMAPPING_DUPLICATE_SOURCE( "Source value mapping: \"%s\" cannot be mapped more than once." ),
    VALUEMAPPING_ANY_AREADY_DEFINED( "Source = \"<ANY_REMAINING>\" or \"<ANY_UNMAPPED>\" can only be used once." ),
    VALUE_MAPPING_UNMAPPED_SOURCES( "The following constants from the %s enum have no corresponding constant in the %s enum and must be be mapped via adding additional mappings: %s." ),
    VALUEMAPPING_NON_EXISTING_CONSTANT( "Constant %s doesn't exist in enum type %s." );

    // CHECKSTYLE:ON

    private final String description;
    private final Diagnostic.Kind kind;

    Message(String description) {
        this.description = description;
        this.kind = Diagnostic.Kind.ERROR;
    }

    Message(String description, Diagnostic.Kind kind) {
        this.description = description;
        this.kind = kind;
    }

    public String getDescription() {
        return description;
    }

    public Diagnostic.Kind getDiagnosticKind() {
        return kind;
    }
}
