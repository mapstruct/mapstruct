/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

import static org.mapstruct.ap.internal.util.Strings.getSafeVariableName;
import static org.mapstruct.ap.internal.util.Strings.join;

/**
 * A method implemented or referenced by a {@link Mapper} class.
 *
 * @author Gunnar Morling
 */
public abstract class MappingMethod extends GeneratedTypeMethod {

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
        this.afterMappingReferences = afterMappingReferences == null ? Collections.emptyList() : afterMappingReferences;
    }

    protected MappingMethod(Method method, List<Parameter> parameters) {
        this( method, parameters, new ArrayList<>( method.getParameterNames() ), null, null );
    }

    protected MappingMethod(Method method) {
        this( method, new ArrayList<>( method.getParameterNames() ), null, null );
    }

    protected MappingMethod(Method method, List<LifecycleCallbackMethodReference> beforeMappingReferences,
                            List<LifecycleCallbackMethodReference> afterMappingReferences) {
        this( method, new ArrayList<>( method.getParameterNames() ), beforeMappingReferences,
            afterMappingReferences );
    }

    private String initResultName(Collection<String> existingVarNames) {
        if ( targetParameter != null ) {
            return targetParameter.getName();
        }
        else if ( getResultType().isArrayType() ) {
            String name = getSafeVariableName( getResultType().getComponentType().getName() + "Tmp", existingVarNames );
            existingVarNames.add( name );
            return name;
        }
        else {
            String name = getSafeVariableName( getResultType().getName(), existingVarNames );
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
        Set<Type> types = new HashSet<>();

        for ( Parameter param : parameters ) {
            types.addAll( param.getType().getImportTypes() );
        }

        types.addAll( getReturnType().getImportTypes() );

        for ( Type type : thrownTypes ) {
            types.addAll( type.getImportTypes() );
        }

        for ( LifecycleCallbackMethodReference reference : beforeMappingReferencesWithMappingTarget ) {
            types.addAll( reference.getImportTypes() );
        }
        for ( LifecycleCallbackMethodReference reference : beforeMappingReferencesWithoutMappingTarget ) {
            types.addAll( reference.getImportTypes() );
        }
        for ( LifecycleCallbackMethodReference reference : afterMappingReferences ) {
            types.addAll( reference.getImportTypes() );
        }

        return types;
    }

    protected List<String> getParameterNames() {
        List<String> parameterNames = new ArrayList<>( parameters.size() );

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

    protected static List<LifecycleCallbackMethodReference> filterMappingTarget(
        List<LifecycleCallbackMethodReference> methods, boolean mustHaveMappingTargetParameter) {
        if ( methods == null ) {
            return Collections.emptyList();
        }

        List<LifecycleCallbackMethodReference> result =
            new ArrayList<>( methods.size() );

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

        if ( !Objects.equals( parameters, other.parameters ) ) {
            return false;
        }

        if ( !Objects.equals( returnType, other.returnType ) ) {
            return false;
        }

        return true;
    }
}
