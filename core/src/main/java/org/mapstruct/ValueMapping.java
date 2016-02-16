/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configures the mapping of source constant value to target constant value.
 *
 * @author Sjaak Derksen
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ValueMapping {

    /**
     * Controls the type of mapping.
     *
     * @return The mapping type.
     */
    ValueMappingType valueMappingType() default ValueMappingType.SOURCE;


    /**
     * The source value constant to use for this mapping.
     *
     * If the value is not of type {@code String}, the value will be converted by applying a matching conversion method
     * or built-in conversion. Enumeration constants will be taken 'as is'.
     *
     * <p>
     * <b>NOTE:</b>
     * <ol>
     * <li> Mandatory for a mapping type {@link ValueMappingType#SOURCE} </li>
     * <li>May not be defined for {@link ValueMappingType#DEFAULT} or {@link ValueMappingType#NULL}</li>
     * </ol>
     *
     * @return The source value.
     */
    String source() default "";

    /**
     * The target value constant to use for this mapping.
     *
     * If the value is not of type {@code String}, the value will be converted by applying a matching
     * conversion method or built-in conversion. Enumeration constants will be taken 'as is'.
     *
     * This is a mandatory property unless {@code target=true}
     *
     * @return The target value.
     */
    String target() default "";

    /**
     * The {@link #target) should be considered {@code null}
     *
     * @return true if {@link #target) is to be considered {@code null}
     */
    boolean targetIsNull() default false;

}
