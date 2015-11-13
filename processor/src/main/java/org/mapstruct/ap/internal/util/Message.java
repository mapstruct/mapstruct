/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
    BEANMAPPING_NO_ELEMENTS( "'nullValueMappingStrategy', 'resultType' and 'qualifiedBy' are undefined in @BeanMapping, define at least one of them." ),
    BEANMAPPING_NOT_ASSIGNABLE( "%s not assignable to: %s." ),
    BEANMAPPING_UNKNOWN_PROPERTY_IN_RETURNTYPE( "Unknown property \"%s\" in return type." ),
    BEANMAPPING_SEVERAL_POSSIBLE_SOURCES( "Several possible source properties for target property \"%s\"." ),
    BEANMAPPING_SEVERAL_POSSIBLE_TARGET_ACCESSORS( "Found several matching getters for property \"%s\"." ),
    BEANMAPPING_UNMAPPED_TARGETS_WARNING( "Unmapped target %s.", Diagnostic.Kind.WARNING ),
    BEANMAPPING_UNMAPPED_TARGETS_ERROR( "Unmapped target %s." ),
    BEANMAPPING_CYCLE_BETWEEN_PROPERTIES( "Cycle(s) between properties given via dependsOn(): %s." ),
    BEANMAPPING_UNKNOWN_PROPERTY_IN_DEPENDS_ON( "\"%s\" is no property of the method return type." ),

    PROPERTYMAPPING_MAPPING_NOT_FOUND( "Can't map %s to \"%s %s\". Consider to declare/implement a mapping method: \"%s map(%s value)\"." ),
    PROPERTYMAPPING_DUPLICATE_TARGETS( "Target property \"%s\" must not be mapped more than once." ),
    PROPERTYMAPPING_EMPTY_TARGET( "Target must not be empty in @Mapping." ),
    PROPERTYMAPPING_SOURCE_AND_CONSTANT_BOTH_DEFINED( "Source and constant are both defined in @Mapping, either define a source or a constant." ),
    PROPERTYMAPPING_SOURCE_AND_EXPRESSION_BOTH_DEFINED( "Source and expression are both defined in @Mapping, either define a source or an expression." ),
    PROPERTYMAPPING_EXPRESSION_AND_CONSTANT_BOTH_DEFINED( "Expression and constant are both defined in @Mapping, either define an expression or a constant." ),
    PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_VALUE_BOTH_DEFINED( "Expression and default value are both defined in @Mapping, either define a defaultValue or an expression." ),
    PROPERTYMAPPING_CONSTANT_AND_DEFAULT_VALUE_BOTH_DEFINED( "Constant and default value are both defined in @Mapping, either define a defaultValue or a constant." ),
    PROPERTYMAPPING_INVALID_EXPRESSION( "Value must be given in the form \"java(<EXPRESSION>)\"." ),
    PROPERTYMAPPING_REVERSAL_PROBLEM( "Parameter %s cannot be reversed." ),
    PROPERTYMAPPING_INVALID_PARAMETER_NAME( "Method has no parameter named \"%s\"." ),
    PROPERTYMAPPING_NO_PROPERTY_IN_PARAMETER( "The type of parameter \"%s\" has no property named \"%s\"." ),
    PROPERTYMAPPING_INVALID_PROPERTY_NAME( "No property named \"%s\" exists in source parameter(s)." ),
    PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE( "No read accessor found for property \"%s\" in target type." ),

    CONSTANTMAPPING_MAPPING_NOT_FOUND( "Can't map \"%s %s\" to \"%s %s\"." ),
    CONSTANTMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE( "No read accessor found for property \"%s\" in target type." ),

    MAPMAPPING_KEY_MAPPING_NOT_FOUND( "No implementation can be generated for this method. Found no method nor implicit conversion for mapping source key type to target key type." ),
    MAPMAPPING_VALUE_MAPPING_NOT_FOUND( "No implementation can be generated for this method. Found no method nor implicit conversion for mapping source value type to target value type." ),
    MAPMAPPING_NO_ELEMENTS( "'nullValueMappingStrategy', 'keyDateFormat', 'keyQualifiedBy', 'keyTargetType', 'valueDateFormat', 'valueQualfiedBy' and 'valueTargetType' are all undefined in @MapMapping, define at least one of them." ),

    ITERABLEMAPPING_MAPPING_NOT_FOUND( "No implementation can be generated for this method. Found no method nor implicit conversion for mapping source element type into target element type." ),
    ITERABLEMAPPING_NO_ELEMENTS( "'nullValueMappingStrategy','dateformat', 'qualifiedBy' and 'elementTargetType' are undefined in @IterableMapping, define at least one of them." ),

    ENUMMAPPING_MULTIPLE_TARGETS( "One enum constant must not be mapped to more than one target constant, but constant %s is mapped to %s." ),
    ENUMMAPPING_UNDEFINED_SOURCE( "A source constant must be specified for mappings of an enum mapping method." ),
    ENUMMAPPING_NON_EXISTING_CONSTANT( "Constant %s doesn't exist in enum type %s." ),
    ENUMMAPPING_UNDEFINED_TARGET( "A target constant must be specified for mappings of an enum mapping method." ),
    ENUMMAPPING_UNMAPPED_TARGETS( "The following constants from the source enum have no corresponding constant in the target enum and must be be mapped via @Mapping: %s." ),

    DECORATOR_NO_SUBTYPE( "Specified decorator type is no subtype of the annotated mapper type." ),
    DECORATOR_CONSTRUCTOR( "Specified decorator type has no default constructor nor a constructor with a single parameter accepting the decorated mapper type." ),

    GENERAL_NO_IMPLEMENTATION( "No implementation type is registered for return type %s." ),
    GENERAL_AMBIGIOUS_MAPPING_METHOD( "Ambiguous mapping methods found for mapping %s to %s: %s." ),
    GENERAL_AMBIGIOUS_FACTORY_METHOD( "Ambiguous factory methods found for creating %s: %s." ),
    GENERAL_UNSUPPORTED_DATE_FORMAT_CHECK( "No dateFormat check is supported for types %s, %s" ),
    GENERAL_VALID_DATE( "Given date format \"%s\" is valid.", Diagnostic.Kind.NOTE ),
    GENERAL_INVALID_DATE( "Given date format \"%s\" is invalid. Message: \"%s\"." ),

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



    INHERITCONFIGURATION_BOTH( "Method cannot be annotated with both a @InheritConfiguration and @InheritInverseConfiguration." ),
    INHERITINVERSECONFIGURATION_DUPLICATES( "Several matching inverse methods exist: %s(). Specify a name explicitly." ),
    INHERITINVERSECONFIGURATION_INVALID_NAME( "None of the candidates %s() matches given name: \"%s\"." ),
    INHERITINVERSECONFIGURATION_DUPLICATE_MATCHES( "Given name \"%s\" matches several candidate methods: %s." ),
    INHERITINVERSECONFIGURATION_NO_NAME_MATCH( "Given name \"%s\" does not match the only candidate. Did you mean: \"%s\"." ),
    INHERITCONFIGURATION_DUPLICATES( "Several matching methods exist: %s(). Specify a name explicitly." ),
    INHERITCONFIGURATION_INVALIDNAME( "None of the candidates %s() matches given name: \"%s\"." ),
    INHERITCONFIGURATION_DUPLICATE_MATCHES( "Given name \"%s\" matches several candidate methods: %s." ),
    INHERITCONFIGURATION_NO_NAME_MATCH( "Given name \"%s\" does not match the only candidate. Did you mean: \"%s\"." ),
    INHERITCONFIGURATION_MULTIPLE_PROTOTYPE_METHODS_MATCH( "More than one configuration prototype method is applicable. Use @InheritConfiguration to select one of them explicitly: %s." ),
    INHERITCONFIGURATION_CYCLE( "Cycle detected while evaluating inherited configurations. Inheritance path: %s" );
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
