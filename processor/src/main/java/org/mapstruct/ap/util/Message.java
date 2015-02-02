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
package org.mapstruct.ap.util;

/**
 *
 * @author Sjaak Derksen
 */
public enum Message {

    // CHECKSTYLE:OFF
    beanmapping_noelements( "'resultType' and 'qualifiedBy' are undefined in @BeanMapping, define at least one of them." ),
    beanmapping_notassignable( "%s not assignable to: %s." ),
    beanmapping_unknownpropertyinreturntype( "Unknown property \"%s\" in return type." ),
    beanmapping_severalpossiblesources( "Several possible source properties for target property \"%s\"." ),
    beanmapping_severalpossibletargetaccessors( "Found several matching getters for property \"%s\"." ),
    beanmapping_unmappedtargets( "Unmapped target %s." ),
    propertymapping_mappingnotfound( "Can't map %s to \"%s %s\". Consider to declare/implement a mapping method: \"%s map(%s value)\"." ),
    propertymapping_duplicatetargets( "Target property \"%s\" must not be mapped more than once." ),
    propertymapping_emptytarget( "Target must not be empty in @Mapping." ),
    propertymapping_sourceandconstantbothdefined( "Source and constant are both defined in @Mapping, either define a source or a constant." ),
    propertymapping_sourceandexpressionbothdefined( "Source and expression are both defined in @Mapping, either define a source or an expression." ),
    propertymapping_expressionandconstantbothdefined( "Expression and constant are both defined in @Mapping, either define an expression or a constant." ),
    propertymapping_invalidexpression( "Value must be given in the form \"java(<EXPRESSION>)\"." ),
    propertymapping_reversalproblem( "Parameter %s cannot be reversed." ),
    propertymapping_invalidparametername( "Method has no parameter named \"%s\"." ),
    propertymapping_nopropertyinparameter( "The type of parameter \"%s\" has no property named \"%s\"." ),
    propertymapping_invalidpropertyname( "No property named \"%s\" exists in source parameter(s)." ),
    constantmapping_mappingnotfound( "Can't map \"%s %s\" to \"%s %s\"." ),
    mapmapping_key_mappingnotfound( "Can't create implementation of method %s. Found no method nor built-in conversion for mapping source key type to target key type." ),
    mapmapping_value_mappingnotfound( "Can't create implementation of method %s. Found no method nor built-in conversion for mapping source value type to target value type." ),
    mapmapping_noelements( "'keyDateFormat', 'keyQualifiedBy', 'keyTargetType', 'valueDateFormat', 'valueQualfiedBy' and 'valueTargetType' are all undefined in @MapMapping, define at least one of them." ),
    iterablemapping_mappingnotfound( "Can't create implementation of method %s. Found no method nor built-in conversion for mapping source element type into target element type." ),
    iterablemapping_noelements( "'dateformat', 'qualifiedBy' and 'elementTargetType' are undefined in @IterableMapping, define at least one of them." ),
    enummapping_multipletargets( "One enum constant must not be mapped to more than one target constant, but constant %s is mapped to %s." ),
    enummapping_undefinedsource( "A source constant must be specified for mappings of an enum mapping method." ),
    enummapping_nonexistingconstant( "Constant %s doesn't exist in enum type %s." ),
    enummapping_undefinedtarget( "A target constant must be specified for mappings of an enum mapping method." ),
    enummapping_unmappedtargets( "The following constants from the source enum have no corresponding constant in the target enum and must be be mapped via @Mapping: %s." ),
    decorator_nosubtype( "Specified decorator type is no subtype of the annotated mapper type." ),
    decorator_constructor( "Specified decorator type has no default constructor nor a constructor with a single parameter accepting the decorated mapper type." ),
    general_noimplementation( "No implementation type is registered for return type %s." ),
    general_ambigiousmappingmethod( "Ambiguous mapping methods found for mapping %s to %s: %s." ),
    general_ambigiousfactorymethod( "Ambiguous mapping methods found for factorizing %s: %s." ),
    general_unsupporteddateformatcheck( "No dateFormat check is supported for types %s, %s" ),
    general_validdate( "given date format \"%s\" is valid." ),
    general_invaliddate( "given date format \"%s\" is invalid. Message: \"%s\"." ),
    retrieval_noinputargs( "Can't generate mapping method with no input arguments." ),
    retrieval_duplicatemappingtargets( "Can't generate mapping method with more than one @MappingTarget parameter." ),
    retrieval_voidmappingmethod( "Can't generate mapping method with return type void." ),
    retrieval_nonassignableresulttype( "The result type is not assignable to the the return type." ),
    retrieval_iterabletononiterable( "Can't generate mapping method from iterable type to non-iterable type." ),
    retrieval_mappinghastargettypeparameter( "Can't generate mapping method that has a parameter annotated with @TargetType." ),
    retrieval_noniterabletoiterable( "Can't generate mapping method from non-iterable type to iterable type." ),
    retrieval_primitiveparameter( "Can't generate mapping method with primitive parameter type." ),
    retrieval_primitivereturn( "Can't generate mapping method with primitive return type." ),
    retrieval_enumtononenum( "Can't generate mapping method from enum type to non-enum type." ),
    retrieval_nonenumtoenum( "Can't generate mapping method from non-enum type to enum type." ),
    inheritconfiguration_both( "Method cannot be annotated with both a @InheritConfiguration and @InheritInverseConfiguration." ),
    inheritinverseconfiguration_referencehasinverse( "Resolved inverse mapping method %s() should not carry the @InheritInverseConfiguration annotation itself." ),
    inheritinverseconfiguration_referencehasforward( "Resolved inverse mapping method %s() should not carry the @InheritConfiguration annotation." ),
    inheritinverseconfiguration_duplicates( "Several matching inverse methods exist: %s(). Specify a name explicitly." ),
    inheritinverseconfiguration_invalidname( "None of the candidates %s() matches given name: \"%s\"." ),
    inheritinverseconfiguration_duplicatematches( "Given name \"%s\" matches several candidate methods: %s()." ),
    inheritinverseconfiguration_nonamematch( "Given name \"%s\" does not match the only candidate. Did you mean: \"%s\"." ),
    inheritconfiguration_referencehasforward( "Resolved mapping method %s() should not carry the @InheritConfiguration annotation itself." ),
    inheritconfiguration_referencehasinverse( "Resolved mapping method %s() should not carry the @InheritInverseConfiguration annotation." ),
    inheritconfiguration_duplicates( "Several matching methods exist: %s(). Specify a name explicitly." ),
    inheritconfiguration_invalidname( "None of the candidates %s() matches given name: \"%s\"." ),
    inheritconfiguration_duplicatematches( "Given name \"%s\" matches several candidate methods: %s()." ),
    inheritconfiguration_nonamematch( "Given name \"%s\" does not match the only candidate. Did you mean: \"%s\"." );
    // CHECKSTYLE:ON

    private final String description;

    private Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
