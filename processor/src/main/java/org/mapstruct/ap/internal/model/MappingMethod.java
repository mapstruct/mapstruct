/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import static org.mapstruct.ap.internal.util.Strings.getSafeVariableName;
import static org.mapstruct.ap.internal.util.Strings.join;

import java.util.*;

import org.mapstruct.ap.internal.model.common.*;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

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

    private final List<Annotation> methodAnnotations;

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
        this.methodAnnotations = getSourceMethodAnnotations(method);
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

        for (Annotation methodAnnotation : methodAnnotations) {
            types.addAll(methodAnnotation.getImportTypes());
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

    private List<LifecycleCallbackMethodReference> filterMappingTarget(List<LifecycleCallbackMethodReference> methods,
                                                                       boolean mustHaveMappingTargetParameter) {
        if ( methods == null ) {
            return null;
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

    public List<Annotation> getMethodAnnotations() {
        return methodAnnotations;
    }

    /**
     * Get annotations for non-mapstruct frameworks in methods
     *
     * @return annotations
     */
    private List<Annotation> getSourceMethodAnnotations(Method method){
        List<Annotation> annotationTypes = new ArrayList<>();
        if (method instanceof SourceMethod) {
            SourceMethod sourceMethod = (SourceMethod) method;
            List<? extends AnnotationMirror> annotationMirrors = sourceMethod.getExecutable().getAnnotationMirrors();
            TypeFactory typeFactory = sourceMethod.getTypeFactory();
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                Type type = typeFactory.getType(annotationMirror.getAnnotationType());
                if (isInternalAnnotation(type)){
                    continue;
                }
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
                if (elementValues.isEmpty()){
                    annotationTypes.add(new Annotation(type));
                    continue;
                }
                List<String> properties = new ArrayList<>();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                    ExecutableElement key = entry.getKey();
                    AnnotationValue value = entry.getValue();
                    properties.add(key.getSimpleName().toString()+" = "+value.toString());
                }
                annotationTypes.add(new Annotation(type,properties));
            }
        }
        return annotationTypes;
    }

    private boolean isInternalAnnotation(Type type){
        return type.getPackageName().equalsIgnoreCase("org.mapstruct");
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
