/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.model.common.Accessibility;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.BasicMethod;
import org.mapstruct.ap.util.Strings;

/**
 * Implementations create:
 * 1) an implementation of this build in method.
 * 2) a reference to a build in method, to use in property mappings
 * 3) a name for logging purposes.
 *
 * @author Sjaak Derksen
 */
public abstract class BuiltInMethod extends ModelElement implements BasicMethod {


    /**
     * method name
     * @return default method name is equal to class name of build in mehtod
     */
    @Override
    public String getName() {
        return Strings.decapitalize( this.getClass().getSimpleName() );
    }

    /**
     * imported types default. Only used types should be added. Source and Target types are coming via
     * the MethodReference
     *
     * @return set of used types.
     */
    @Override
    public Set<Type> getImportTypes() {
        return Collections.<Type>emptySet();
    }

    @Override
    public boolean matches( Type sourceType, Type targetType ) {
        if ( targetType.erasure().isAssignableTo( getReturnType().erasure() )
                && sourceType.erasure().isAssignableTo( getParameter().getType().erasure() ) ) {
            return doTypeVarsMatch( sourceType, targetType );
        }
        return false;
    }

    /**
     *
     * @return all parameters are source parameters for build-in methods.
     */
    @Override
    public List<Parameter> getSourceParameters() {
        return getParameters();
    }

    /**
     * declaring mapper is always null, being the MapperImpl
     * @return null
     */
    @Override
    public Type getDeclaringMapper() {
        return null;
    }

    @Override
    public List<Parameter> getParameters() {
        return Arrays.asList( new Parameter[] { getParameter() } );
    }

    /**
     * target parameter mechanism not supported for build-in-method
     * @return null
     */
    @Override
    public Parameter getTargetParameter() {
        return null;
    }

    /**
     * the conversion context is used to format an auxiliary parameter in the method call
     * with context specific information such as a date format.
     *
     * @param conversionContext
     * @return null if no context parameter should be included
     *         "null" if there should be an explicit null call
     *         "'dateFormat'" for instance, to indicate how the build-in method should format the date
     */
    public String getContextParameter(ConversionContext conversionContext) {
        return null;
    }

    /**
     * hashCode
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
     * @return true when classes are the same
     */
    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        return ( getClass() == obj.getClass() );
    }

    /**
     * Analyzes the Java Generics type variables in the parameter do match the type variables in the build in method
     * same goes for the returnType.
     *
     * @param parameter source
     * @param returnType target
     * @return
     */
    public boolean doTypeVarsMatch( Type parameter, Type returnType ) {
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
}
