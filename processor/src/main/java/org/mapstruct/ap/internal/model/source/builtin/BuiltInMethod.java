/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Represents a "built-in" mapping method which will be added as private method to the generated mapper. Built-in
 * methods are used in cases where a simple conversation doesn't suffice, e.g. as several lines of source code or a
 * try/catch block are required.
 *
 * @author Sjaak Derksen
 */
public abstract class BuiltInMethod implements Method {

    /**
     * {@inheritDoc }
     *
     * @return default method name is equal to class name of build in method name
     */
    @Override
    public String getName() {
        return Strings.decapitalize( this.getClass().getSimpleName() );
    }

    /**
     * Returns the types used by this method for which import statements need to be generated. Defaults to the empty
     * set. To be overridden by implementations in case they make use of additional types (note that the parameter and
     * return type don't need to be added).
     *
     * @return the types used by this method for which import statements need to be generated
     */
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default the targetType should be assignable to the returnType and the sourceType to the parameter,
     * excluding generic type variables. When the implementor sees a need for this, this method can be overridden.
     */
    @Override
    public boolean matches(List<Type> sourceTypes, Type targetType) {
        if ( sourceTypes.size() != 1 ) {
            return false;
        }

        Type sourceType = first( sourceTypes );

        Type returnType = getReturnType().resolveParameterToType( sourceType, getParameter().getType() ).getMatch();
        if ( returnType == null ) {
            return false;
        }

        return returnType.isAssignableTo( targetType )
            && sourceType.erasure().isAssignableTo( getParameter().getType() );
    }

    @Override
    public List<Parameter> getSourceParameters() {
        return getParameters();
    }

    @Override
    public List<Parameter> getContextParameters() {
        return Collections.emptyList();
    }

    @Override
    public ParameterProvidedMethods getContextProvidedMethods() {
        return ParameterProvidedMethods.empty();
    }

    /**
     * {@inheritDoc}
     * <p>
     * For built-in methods, the declaring mapper is always {@code null} as they will be added as private methods to the
     * generated mapper.
     *
     * @return {@code null}
     */
    @Override
    public final Type getDeclaringMapper() {
        return null;
    }

    @Override
    public List<Parameter> getParameters() {
        return Arrays.asList( getParameter() );
    }

    /**
     * mapping target parameter mechanism not supported for built-in methods
     *
     * @return {@code null}
     */
    @Override
    public Parameter getMappingTargetParameter() {
        return null;
    }

    /**
     * target type parameter mechanism not supported for built-in methods
     *
     * @return {@code null}
     */
    @Override
    public Parameter getTargetTypeParameter() {
        return null;
    }

    /**
     * object factory mechanism not supported for built-in methods
     *
     * @return false
     */
    @Override
    public boolean isObjectFactory() {
        return false;
    }

    /**
     * the conversion context is used to format an auxiliary parameter in the method call with context specific
     * information such as a date format.
     *
     * @param conversionContext context
     * @return null if no context parameter should be included "null" if there should be an explicit null call
     *         "'dateFormat'" for instance, to indicate how the build-in method should format the date
     */
    public String getContextParameter(ConversionContext conversionContext) {
        return null;
    }

    @Override
    public List<Type> getTypeParameters() {
        return Collections.emptyList();
    }

    /**
     * hashCode based on class
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    /**
     * equals based on class
     *
     * @param obj other class
     *
     * @return true when classes are the same
     */
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        return ( getClass() == obj.getClass() );
    }

    /**
     * Analyzes the Java Generic type variables in the parameter do match the type variables in the build in method same
     * goes for the returnType.
     *
     * @param parameter source
     * @param returnType target
     * @return {@code true}, iff the the type variables match
     */
    public boolean doTypeVarsMatch(Type parameter, Type returnType) {
        return true;
    }

    /**
     * There's currently only one parameter foreseen instead of a list of parameter
     *
     * @return the parameter
     */
    public abstract Parameter getParameter();

    @Override
    public Accessibility getAccessibility() {
        return Accessibility.PRIVATE;
    }

    @Override
    public List<Type> getThrownTypes() {
        return Collections.emptyList();
    }

    @Override
    public Type getResultType() {
        return getReturnType();
    }

    @Override
    public List<String> getParameterNames() {
        List<String> parameterNames = new ArrayList<>( getParameters().size() );
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
        return null;
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
    public boolean isLifecycleCallbackMethod() {
        return false;
    }

    @Override
    public boolean isUpdateMethod() {
        return getMappingTargetParameter() != null;
    }

    @Override
    public MappingMethodOptions getOptions() {
        return MappingMethodOptions.empty();
    }

    public BuiltInFieldReference getFieldReference() {
        return null;
    }

    public BuiltInConstructorFragment getConstructorFragment() {
        return null;
    }

    @Override
    public String describe() {
        // the name of the builtin method is never fully qualified, so no need to distinguish
        // between verbose or not. The type knows whether it should log verbose
        return getResultType().describe() + ":" + getName() + "(" + getMappingSourceType().describe() + ")";
    }
}
