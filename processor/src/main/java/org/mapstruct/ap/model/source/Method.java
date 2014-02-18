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

package org.mapstruct.ap.model.source;

import java.util.List;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;

/**
 * This interface makes available common method properties and a matching method
 *
 * There are 2 known implementors: {@link BuiltInMethod} and {@link Method}
 * @author Sjaak Derksen
 */
public interface Method {

    /**
     * Checks whether the provided sourceType and provided targetType match with the parameter respectively
     * return type of the method. The check also should incorporate wild card and generic type variables
     *
     * @param sourceType the sourceType to match to the parameter
     * @param targetType the targetType to match to the returnType
     *
     * @return true when match
     */
    boolean matches( Type sourceType, Type targetType );

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
     * target by means of the target annotation {@link  #getTargetParameter() }.
     *
     * @return list of 'true' source parameters
     */
    List<Parameter> getSourceParameters();

    /**
     * Returns the parameter designated as target parameter (if present) {@link #getSourceParameters() }
     * @return target parameter (when present) null otherwise.
     */
    Parameter getTargetParameter();

    /**
     * Returns the return type of the method
     *
     * @return return type
     */
    Type getReturnType();


}
