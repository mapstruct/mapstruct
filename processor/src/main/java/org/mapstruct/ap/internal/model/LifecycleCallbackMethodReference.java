/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Represents a reference to a method that is annotated with {@code @BeforeMapping} or {@code @AfterMapping}.
 *
 * @author Andreas Gudian
 */
public class LifecycleCallbackMethodReference extends MethodReference {

    private final Type declaringType;
    private final Type methodReturnType;
    private final Type methodResultType;
    private final String targetVariableName;

    private LifecycleCallbackMethodReference(SelectedMethod<SourceMethod> lifecycleMethod,
                                             MapperReference mapperReference, Parameter providingParameter,
                                             Method containingMethod, Set<String> existingVariableNames) {
        super(
            lifecycleMethod.getMethod(),
            mapperReference,
            providingParameter,
            lifecycleMethod.getParameterBindings() );

        this.declaringType = lifecycleMethod.getMethod().getDeclaringMapper();
        this.methodReturnType = containingMethod.getReturnType();
        this.methodResultType = containingMethod.getResultType();

        if ( hasReturnType() ) {
            this.targetVariableName = Strings.getSafeVariableName( "target", existingVariableNames );
            existingVariableNames.add( this.targetVariableName );
        }
        else {
            this.targetVariableName = null;
        }
    }

    public Type getDeclaringType() {
        return declaringType;
    }

    /**
     * Returns the return type of the mapping method in which this callback method is called
     *
     * @return return type
     * @see Method#getReturnType()
     */
    public Type getMethodReturnType() {
        return methodReturnType;
    }

    /**
     * Returns the result type of the mapping method in which this callback method is called
     *
     * @return result type
     * @see Method#getResultType()
     */
    public Type getMethodResultType() {
        return methodResultType;
    }

    public String getTargetVariableName() {
        return targetVariableName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return declaringType != null ? Collections.asSet( declaringType ) : java.util.Collections.emptySet();
    }

    public boolean hasMappingTargetParameter() {
        return getParameterBindings().stream().anyMatch( ParameterBinding::isMappingTarget );
    }
    
    public boolean hasResultMappingTargetParameter() {
        return getParameterBindings().stream().anyMatch( ParameterBinding::isResultMappingTarget );
    }

    /**
     * @return true if this callback method has a return type that is not void
     */
    public boolean hasReturnType() {
        return !getReturnType().isVoid();
    }

    public static LifecycleCallbackMethodReference forParameterProvidedMethod(
            SelectedMethod<SourceMethod> lifecycleMethod,
            Parameter providingParameter, Method containingMethod,
            Set<String> existingVariableNames) {

        return new LifecycleCallbackMethodReference(
            lifecycleMethod,
            null,
            providingParameter,
            containingMethod,
            existingVariableNames );
    }

    public static LifecycleCallbackMethodReference forMethodReference(SelectedMethod<SourceMethod> lifecycleMethod,
            MapperReference mapperReference, Method containingMethod,
            Set<String> existingVariableNames) {

        return new LifecycleCallbackMethodReference(
            lifecycleMethod,
            mapperReference,
            null,
            containingMethod,
            existingVariableNames );
    }
}
