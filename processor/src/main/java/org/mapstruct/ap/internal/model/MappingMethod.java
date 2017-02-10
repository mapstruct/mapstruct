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
package org.mapstruct.ap.internal.model;

import static org.mapstruct.ap.internal.util.Strings.getSaveVariableName;
import static org.mapstruct.ap.internal.util.Strings.join;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * A method implemented or referenced by a {@link Mapper} class.
 *
 * @author Gunnar Morling
 */
public abstract class MappingMethod extends ModelElement {

    private final String name;
    private final List<Parameter> parameters;
    private final List<Parameter> sourceParameters;
    private final Type returnType;
    private final Parameter targetParameter;
    private final Accessibility accessibility;
    private final List<Type> thrownTypes;
    private final boolean isStatic;
    private final String resultName;
    private final List<LifecycleCallbackMethodReference> beforeMappingReferencesWithMappingTarget;
    private final List<LifecycleCallbackMethodReference> beforeMappingReferencesWithoutMappingTarget;
    private final List<LifecycleCallbackMethodReference> afterMappingReferences;

    /**
     * constructor to be overloaded when local variable names are required prior to calling this constructor. (e.g. for
     * property mappings). It is supposed to be initialized with at least the parameter names.
     *
     * @param method the method for which this mapping is applicable
     * @param existingVariableNames set of already assigned variable names
     * @param beforeMappingReferences all life cycle methods to be called prior to carrying out mapping
     * @param afterMappingReferences all life cycle methods to be called after carrying out mapping
     */
    protected MappingMethod(Method method, Collection<String> existingVariableNames,
                            List<LifecycleCallbackMethodReference> beforeMappingReferences,
                            List<LifecycleCallbackMethodReference> afterMappingReferences) {
        this( method, method.getParameters(), existingVariableNames, beforeMappingReferences, afterMappingReferences );
    }

    protected MappingMethod(Method method, List<Parameter> parameters, Collection<String> existingVariableNames,
                            List<LifecycleCallbackMethodReference> beforeMappingReferences,
        List<LifecycleCallbackMethodReference> afterMappingReferences) {
        this.name = method.getName();
        this.parameters = parameters;
        this.sourceParameters = Parameter.getSourceParameters( parameters );
        this.returnType = method.getReturnType();
        this.targetParameter = method.getMappingTargetParameter();
        this.accessibility = method.getAccessibility();
        this.thrownTypes = method.getThrownTypes();
        this.isStatic = method.isStatic();
        this.resultName = initResultName( existingVariableNames );
        this.beforeMappingReferencesWithMappingTarget = filterMappingTarget( beforeMappingReferences, true );
        this.beforeMappingReferencesWithoutMappingTarget = filterMappingTarget( beforeMappingReferences, false );
        this.afterMappingReferences = afterMappingReferences;
    }

    protected MappingMethod(Method method, List<Parameter> parameters) {
        this( method, parameters, new ArrayList<String>( method.getParameterNames() ), null, null );
    }

    protected MappingMethod(Method method) {
        this( method, new ArrayList<String>( method.getParameterNames() ), null, null );
    }

    protected MappingMethod(Method method, List<LifecycleCallbackMethodReference> beforeMappingReferences,
                            List<LifecycleCallbackMethodReference> afterMappingReferences) {
        this( method, new ArrayList<String>( method.getParameterNames() ), beforeMappingReferences,
            afterMappingReferences );
    }

    private String initResultName(Collection<String> existingVarNames) {
        if ( targetParameter != null ) {
            return targetParameter.getName();
        }
        else if ( getResultType().isArrayType() ) {
            String name = getSaveVariableName( getResultType().getComponentType().getName() + "Tmp", existingVarNames );
            existingVarNames.add( name );
            return name;
        }
        else {
            String name = getSaveVariableName( getResultType().getName(), existingVarNames );
            existingVarNames.add( name );
            return name;
        }
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<Parameter> getSourceParameters() {
        return sourceParameters;
    }

    public Type getResultType() {
        return targetParameter != null ? targetParameter.getType() : returnType;
    }

    public String getResultName() {
        return resultName;
    }

    public Type getReturnType() {
        return returnType;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public boolean isExistingInstanceMapping() {
        return targetParameter != null;
    }

    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();

        for ( Parameter param : parameters ) {
            types.addAll( param.getType().getImportTypes() );
        }

        types.addAll( getReturnType().getImportTypes() );

        for ( Type type : thrownTypes ) {
            types.addAll( type.getImportTypes() );
        }

        return types;
    }

    protected List<String> getParameterNames() {
        List<String> parameterNames = new ArrayList<String>( parameters.size() );

        for ( Parameter parameter : parameters ) {
            parameterNames.add( parameter.getName() );
        }

        return parameterNames;
    }

    public List<Type> getThrownTypes() {
        return thrownTypes;
    }

    @Override
    public String toString() {
        return returnType + " " + getName() + "(" + join( parameters, ", " ) + ")";
    }

    private List<LifecycleCallbackMethodReference> filterMappingTarget(List<LifecycleCallbackMethodReference> methods,
                                                                       boolean mustHaveMappingTargetParameter) {
        if ( methods == null ) {
            return null;
        }

        List<LifecycleCallbackMethodReference> result =
            new ArrayList<LifecycleCallbackMethodReference>( methods.size() );

        for ( LifecycleCallbackMethodReference method : methods ) {
            if ( mustHaveMappingTargetParameter == method.hasMappingTargetParameter() ) {
                result.add( method );
            }
        }

        return result;
    }

    public List<LifecycleCallbackMethodReference> getAfterMappingReferences() {
        return afterMappingReferences;
    }

    public List<LifecycleCallbackMethodReference> getBeforeMappingReferencesWithMappingTarget() {
        return beforeMappingReferencesWithMappingTarget;
    }

    public List<LifecycleCallbackMethodReference> getBeforeMappingReferencesWithoutMappingTarget() {
        return beforeMappingReferencesWithoutMappingTarget;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.parameters != null ? this.parameters.hashCode() : 0);
        hash = 83 * hash + (this.returnType != null ? this.returnType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        //Do not add name to the equals check.
        //Reason: Whenever we forge methods we can reuse mappings if they are the same. However, if we take the name
        // into consideration, they'll never be the same, because we create safe methods names.
        final MappingMethod other = (MappingMethod) obj;
        if ( this.parameters != other.parameters &&
            (this.parameters == null || !this.parameters.equals( other.parameters )) ) {
            return false;
        }
        if ( this.returnType != other.returnType &&
            (this.returnType == null || !this.returnType.equals( other.returnType )) ) {
            return false;
        }
        return true;
    }

}
