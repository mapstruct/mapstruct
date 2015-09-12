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
package org.mapstruct.ap.internal.model.source;

import java.util.List;

import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.internal.util.MapperConfiguration;

/**
 * This interface makes available common method properties and a matching method There are 2 known implementors:
 * {@link BuiltInMethod} and {@link SourceMethod}
 *
 * @author Sjaak Derksen
 */
public interface Method {

    /**
     * Checks whether the provided sourceType and provided targetType match with the parameter respectively return type
     * of the method. The check also should incorporate wild card and generic type variables
     *
     * @param sourceTypes the sourceTypes to match to the parameter
     * @param targetType the targetType to match to the returnType
     *
     * @return true when match
     */
    boolean matches(List<Type> sourceTypes, Type targetType );

    /**
     * Returns the mapper type declaring this method if it is not declared by the mapper interface currently processed
     * but by another mapper imported via {@code Mapper#users()}.
     *
     * @return The declaring mapper type
     */
    Type getDeclaringMapper();

    /**
     * Returns then name of the method.
     *
     * @return method name
     */
    String getName();

    /**
     * In contrast to {@link #getSourceParameters()} this method returns all parameters
     *
     * @return all parameters
     */
    List<Parameter> getParameters();

    /**
     * returns the list of 'true' source parameters excluding the parameter(s) that is designated as
     * target by means of the target annotation {@link  #getMappingTargetParameter() }.
     *
     * @return list of 'true' source parameters
     */
    List<Parameter> getSourceParameters();

    /**
     * Returns the parameter designated as mapping target (if present) {@link  org.mapstruct.MappingTarget }
     *
     * @return mapping target parameter (when present) null otherwise.
     */
    Parameter getMappingTargetParameter();

    /**
     * Returns the parameter designated as target type (if present) {@link org.mapstruct.TargetType }
     *
     * @return target type parameter (when present) null otherwise.
     */
    Parameter getTargetTypeParameter();


    /**
     * Returns the {@link Accessibility} of this method.
     *
     * @return the {@link Accessibility} of this method
     */
    Accessibility getAccessibility();

    /**
     * Returns the return type of the method
     *
     * @return return type
     */
    Type getReturnType();

    /**
     * Returns all exceptions thrown by this method
     *
     * @return exceptions thrown
     */
    List<Type> getThrownTypes();

    /**
     * Returns the type of the result. The result is defined as the type of the parameter designated with
     * {@link org.mapstruct.MappingTarget}, or in absence the return type.
     *
     * @return result type
     */
    Type getResultType();


    /**
     *
     * @return the names of the parameters of this mapping method
     */
    List<String> getParameterNames();

    /**
     * Whether this method overrides an abstract method.
     *
     * @return true when an abstract method is overridden.
     */
    boolean overridesMethod();

    ExecutableElement getExecutable();

    /**
     * Whether this method is static or an instance method
     *
     * @return true when static.
     */
    boolean isStatic();

    /**
     * Whether this method is Java 8 default method
     *
     * @return true when Java 8 default method
     */
    boolean isDefault();

    /**
     *
     *  @return the Type (class or interface) that defines this method.
     */
    Type getDefiningType();

    /**
     *
     * @return the mapper config when this method needs to be implemented
     */
    MapperConfiguration getMapperConfiguration();

    /**
     * @return {@code true}, if the method represents a mapping lifecycle callback (Before/After mapping method)
     */
    boolean isLifecycleCallbackMethod();

    /**
     * @return {@code true}, if the method is an update method, i.e. it has a parameter annotated with
     *         {@code @MappingTarget}.
     */
    boolean isUpdateMethod();
}
