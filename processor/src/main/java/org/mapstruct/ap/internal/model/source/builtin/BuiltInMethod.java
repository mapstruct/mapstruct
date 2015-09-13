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
package org.mapstruct.ap.internal.model.source.builtin;

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.conversion.SimpleConversion;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Represents a "built-in" mapping method which will be added as private method to the generated mapper. Built-in
 * methods are used in cases where a {@link SimpleConversion} doesn't suffice, e.g. as several lines of source code or a
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
        return Collections.<Type>emptySet();
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

        if ( getReturnType().isAssignableTo( targetType.erasure() )
            && sourceType.erasure().isAssignableTo( getParameter().getType() ) ) {
            return doTypeVarsMatch( sourceType, targetType );
        }
        if ( getReturnType().getFullyQualifiedName().equals( "java.lang.Object" )
            && sourceType.erasure().isAssignableTo( getParameter().getType() ) ) {
            // return type could be a type parameter T
            return doTypeVarsMatch( sourceType, targetType );
        }
        if ( getReturnType().isAssignableTo( targetType.erasure() )
            &&  getParameter().getType().getFullyQualifiedName().equals( "java.lang.Object" ) ) {
            // parameter type could be a type parameter T
            return doTypeVarsMatch( sourceType, targetType );
        }
        return false;
    }

    @Override
    public List<Parameter> getSourceParameters() {
        return getParameters();
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
        List<String> parameterNames = new ArrayList<String>( getParameters().size() );
        for ( Parameter parameter : getParameters() ) {
            parameterNames.add( parameter.getName() );
        }

        return parameterNames;
    }

    @Override
    public boolean overridesMethod() {
        return  false;
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
    public MapperConfiguration getMapperConfiguration() {
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
}
