/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.util.Strings;

/**
 * This method will be generated in absence of a suitable abstract method to implement.
 *
 * @author Sjaak Derksen
 */
public class ForgedMethod implements Method {

    private final List<Parameter> parameters;
    private final Type returnType;
    private final String name;
    private final List<Type> thrownTypes;
    private final ForgedMethodHistory history;

    private final List<Parameter> sourceParameters;
    private final List<Parameter> contextParameters;
    private final Parameter mappingTargetParameter;
    private final MappingReferences mappingReferences;

    private final Method basedOn;
    private final boolean forgedNameBased;

    /**
     * Creates a new forged method with the given name for mapping a method parameter to a property.
     *
     * @param name the (unique name) for this method
     * @param sourceType the source type
     * @param returnType the return type.
     * @param basedOn the method that (originally) triggered this nested method generation.
     * @return a new forge method
     */
    public static ForgedMethod forParameterMapping(String name, Type sourceType, Type returnType,
                                                   Method basedOn) {
        return new ForgedMethod(
            name,
            sourceType,
            returnType,
            Collections.emptyList(),
            basedOn,
            null,
            MappingReferences.empty(),
            false
        );
    }

    /**
     * Creates a new forged method for mapping a bean property to a property
     *
     * @param name the (unique name) for this method
     * @param sourceType the source type
     * @param returnType the return type.
     * @param parameters other parameters (including the context + @MappingTarget
     * @param basedOn the method that (originally) triggered this nested method generation.
     * @param history a parent forged method if this is a forged method within a forged method
     * @param mappingReferences the mapping options for this method
     * @param forgedNameBased forges a name based (matched) mapping method
     * @return a new forge method
     */
    public static ForgedMethod forPropertyMapping(String name, Type sourceType, Type returnType,
                                                  List<Parameter> parameters, Method basedOn,
                                                  ForgedMethodHistory history, MappingReferences mappingReferences,
                                                  boolean forgedNameBased) {
        return new ForgedMethod(
            name,
            sourceType,
            returnType,
            parameters,
            basedOn,
            history,
            mappingReferences == null ? MappingReferences.empty() : mappingReferences,
            forgedNameBased
        );
    }

    /**
     * Creates a new forged method for mapping a collection element, map key/value or stream element
     *
     * @param name the (unique name) for this method
     * @param sourceType the source type
     * @param returnType the return type.
     * @param basedOn the method that (originally) triggered this nested method generation.
     * @param history a parent forged method if this is a forged method within a forged method
     * @param forgedNameBased forges a name based (matched) mapping method
     *
     * @return a new forge method
     */
    public static ForgedMethod forElementMapping(String name, Type sourceType, Type returnType, Method basedOn,
                                                 ForgedMethodHistory history, boolean forgedNameBased) {
        return new ForgedMethod(
            name,
            sourceType,
            returnType,
            basedOn.getContextParameters(),
            basedOn,
            history,
            MappingReferences.empty(),
            forgedNameBased
        );
    }

    private ForgedMethod(String name, Type sourceType, Type returnType, List<Parameter> additionalParameters,
                         Method basedOn, ForgedMethodHistory history, MappingReferences mappingReferences,
                         boolean forgedNameBased) {

        // establish name
        String sourceParamSafeName = Strings.getSafeVariableName( sourceType.getName() );

        // establish parameters
        this.parameters = new ArrayList<>( 1 + additionalParameters.size() );
        Parameter sourceParameter = new Parameter( sourceParamSafeName, sourceType );
        this.parameters.add( sourceParameter );
        this.parameters.addAll( additionalParameters );
        this.sourceParameters = Parameter.getSourceParameters( parameters );
        this.contextParameters = Parameter.getContextParameters( parameters );
        this.mappingTargetParameter = Parameter.getMappingTargetParameter( parameters );
        this.returnType = returnType;
        this.thrownTypes = new ArrayList<>();

        // based on method
        this.basedOn = basedOn;

        this.name = Strings.sanitizeIdentifierName( name );
        this.history = history;
        this.mappingReferences = mappingReferences;
        this.forgedNameBased = forgedNameBased;
    }

    /**
     * creates a new ForgedMethod with the same arguments but with a new name
     * @param name the new name
     * @param forgedMethod existing forge method
     */
    public ForgedMethod(String name, ForgedMethod forgedMethod) {
        this.parameters = forgedMethod.parameters;
        this.returnType = forgedMethod.returnType;
        this.thrownTypes = new ArrayList<>();
        this.history = forgedMethod.history;

        this.sourceParameters = Parameter.getSourceParameters( parameters );
        this.contextParameters = Parameter.getContextParameters( parameters );
        this.mappingTargetParameter = Parameter.getMappingTargetParameter( parameters );
        this.mappingReferences = forgedMethod.mappingReferences;

        this.basedOn = forgedMethod.basedOn;

        this.name = name;
        this.forgedNameBased = forgedMethod.forgedNameBased;
    }

    @Override
    public boolean matches(List<Type> sourceTypes, Type targetType) {

        if ( !targetType.equals( returnType ) ) {
            return false;
        }

        if ( parameters.size() != sourceTypes.size() ) {
            return false;
        }

        Iterator<Type> srcTypeIt = sourceTypes.iterator();
        Iterator<Parameter> paramIt = parameters.iterator();

        while ( srcTypeIt.hasNext() && paramIt.hasNext() ) {
            Type sourceType = srcTypeIt.next();
            Parameter param = paramIt.next();
            if ( !sourceType.equals( param.getType() ) ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Type getDeclaringMapper() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public List<Parameter> getSourceParameters() {
        return sourceParameters;
    }

    @Override
    public List<Parameter> getContextParameters() {
        return contextParameters;
    }

    @Override
    public ParameterProvidedMethods getContextProvidedMethods() {
        return basedOn.getContextProvidedMethods();
    }

    @Override
    public Parameter getMappingTargetParameter() {
        return mappingTargetParameter;
    }

    @Override
    public Parameter getTargetTypeParameter() {
        return null;
    }

    @Override
    public Accessibility getAccessibility() {
        return Accessibility.PROTECTED;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public List<Type> getThrownTypes() {
        return thrownTypes;
    }

    public ForgedMethodHistory getHistory() {
        return history;
    }

    public boolean isForgedNamedBased() {
        return forgedNameBased;
    }

    public void addThrownTypes(List<Type> thrownTypesToAdd) {
        for ( Type thrownType : thrownTypesToAdd ) {
            // make sure there are no duplicates coming from the keyAssignment thrown types.
            if ( !thrownTypes.contains( thrownType ) ) {
                thrownTypes.add( thrownType );
            }
        }
    }

    @Override
    public Type getResultType() {
        return mappingTargetParameter != null ? mappingTargetParameter.getType() : returnType;
    }

    @Override
    public List<String> getParameterNames() {
        List<String> parameterNames = new ArrayList<>();
        for ( Parameter parameter : getParameters() ) {
            parameterNames.add( parameter.getName() );
        }
        return parameterNames;
    }

    @Override
    public boolean overridesMethod() {
        return false;
    }

    @Override
    public ExecutableElement getExecutable() {
        return basedOn.getExecutable();
    }

    @Override
    public boolean isLifecycleCallbackMethod() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( returnType.toString() );
        sb.append( " " );

        sb.append( getName() ).append( "(" ).append( Strings.join( parameters, ", " ) ).append( ")" );

        return sb.toString();
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public Type getDefiningType() {
        return null;
    }

    @Override
    public boolean isUpdateMethod() {
        return getMappingTargetParameter() != null;
    }

    /**
     * object factory mechanism not supported for forged methods
     *
     * @return false
     */
    @Override
    public boolean isObjectFactory() {
        return false;
    }

    @Override
    public MappingMethodOptions getOptions() {
        return basedOn.getOptions();
    }

    @Override
    public List<Type> getTypeParameters() {
        return Collections.emptyList();
    }

    @Override
    public String describe() {
        // the name of the forged method is never fully qualified, so no need to distinguish
        // between verbose or not. The type knows whether it should log verbose
        return getResultType().describe() + ":" + getName() + "(" + getMappingSourceType().describe() + ")";
    }

    public MappingReferences getMappingReferences() {
        return mappingReferences;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        ForgedMethod that = (ForgedMethod) o;

        if ( !Objects.equals( parameters, that.parameters ) ) {
            return false;
        }
        return Objects.equals( returnType, that.returnType );

    }

    @Override
    public int hashCode() {
        int result = parameters != null ? parameters.hashCode() : 0;
        result = 31 * result + ( returnType != null ? returnType.hashCode() : 0 );
        return result;
    }
}
