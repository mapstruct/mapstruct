/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.MapperConfiguration;
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
    private final ExecutableElement positionHintElement;
    private final List<Type> thrownTypes;
    private final MapperConfiguration mapperConfiguration;
    private final ForgedMethodHistory history;

    private final List<Parameter> sourceParameters;
    private final List<Parameter> contextParameters;
    private final Parameter mappingTargetParameter;
    private final MappingOptions mappingOptions;
    private final ParameterProvidedMethods contextProvidedMethods;
    private final boolean forgedNameBased;

    /**
     * Creates a new forged method with the given name.
     *
     * @param name the (unique name) for this method
     * @param sourceType the source type
     * @param returnType the return type.
     * @param mapperConfiguration the mapper configuration
     * @param positionHintElement element used to for reference to the position in the source file.
     * @param additionalParameters additional parameters to add to the forged method
     * @param parameterProvidedMethods additional factory/lifecycle methods to consider that are provided by context
     *            parameters
     */
    public ForgedMethod(String name, Type sourceType, Type returnType, MapperConfiguration mapperConfiguration,
                        ExecutableElement positionHintElement, List<Parameter> additionalParameters,
                        ParameterProvidedMethods parameterProvidedMethods) {
        this(
            name,
            sourceType,
            returnType,
            mapperConfiguration,
            positionHintElement,
            additionalParameters,
            parameterProvidedMethods,
            null,
            null,
            false );
    }

     /**
     * Creates a new forged method with the given name.
     *
     * @param name the (unique name) for this method
     * @param sourceType the source type
     * @param returnType the return type.
     * @param mapperConfiguration the mapper configuration
     * @param positionHintElement element used to for reference to the position in the source file.
     * @param additionalParameters additional parameters to add to the forged method
     * @param parameterProvidedMethods additional factory/lifecycle methods to consider that are provided by context
     *            parameters
     * @param history a parent forged method if this is a forged method within a forged method
     * @param mappingOptions the mapping options for this method
     * @param forgedNameBased forges a name based (matched) mapping method
     */
    public ForgedMethod(String name, Type sourceType, Type returnType, MapperConfiguration mapperConfiguration,
                        ExecutableElement positionHintElement, List<Parameter> additionalParameters,
                        ParameterProvidedMethods parameterProvidedMethods, ForgedMethodHistory history,
                        MappingOptions mappingOptions, boolean forgedNameBased) {
        String sourceParamName = Strings.decapitalize( sourceType.getName() );
        String sourceParamSafeName = Strings.getSaveVariableName( sourceParamName );

        this.parameters = new ArrayList<Parameter>( 1 + additionalParameters.size() );
        Parameter sourceParameter = new Parameter( sourceParamSafeName, sourceType );
        this.parameters.add( sourceParameter );
        this.parameters.addAll( additionalParameters );
        this.sourceParameters = Parameter.getSourceParameters( parameters );
        this.contextParameters = Parameter.getContextParameters( parameters );
        this.mappingTargetParameter = Parameter.getMappingTargetParameter( parameters );
        this.contextProvidedMethods = parameterProvidedMethods;

        this.returnType = returnType;
        this.thrownTypes = new ArrayList<Type>();
        this.name = Strings.sanitizeIdentifierName( name );
        this.mapperConfiguration = mapperConfiguration;
        this.positionHintElement = positionHintElement;
        this.history = history;
        this.mappingOptions = mappingOptions == null ? MappingOptions.empty() : mappingOptions;
        this.mappingOptions.initWithParameter( sourceParameter );
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
        this.thrownTypes = new ArrayList<Type>();
        this.mapperConfiguration = forgedMethod.mapperConfiguration;
        this.positionHintElement = forgedMethod.positionHintElement;
        this.history = forgedMethod.history;

        this.sourceParameters = Parameter.getSourceParameters( parameters );
        this.contextParameters = Parameter.getContextParameters( parameters );
        this.mappingTargetParameter = Parameter.getMappingTargetParameter( parameters );
        this.mappingOptions = forgedMethod.mappingOptions;
        this.contextProvidedMethods = forgedMethod.contextProvidedMethods;

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
        return contextProvidedMethods;
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
        List<String> parameterNames = new ArrayList<String>();
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
        return positionHintElement;
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
    public MapperConfiguration getMapperConfiguration() {
        return mapperConfiguration;
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
    public MappingOptions getMappingOptions() {
        return mappingOptions;
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

        if ( parameters != null ? !parameters.equals( that.parameters ) : that.parameters != null ) {
            return false;
        }
        return returnType != null ? returnType.equals( that.returnType ) : that.returnType == null;

    }

    @Override
    public int hashCode() {
        int result = parameters != null ? parameters.hashCode() : 0;
        result = 31 * result + ( returnType != null ? returnType.hashCode() : 0 );
        return result;
    }
}
