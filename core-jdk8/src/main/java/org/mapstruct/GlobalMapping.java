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
package org.mapstruct;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The global mapping configuration is used to avoid repeating the same &#64;{@link Mapping} annotations on different
 * mapping methods. Global mappings can be specified within &#64;{@link Mapper} and &#64;{@link MapperConfig}.
 * <p>
 * The {@link Mapping}s specified within a global mapping are applied to all mapping methods that fulfill the
 * restrictions on source type and target type:
 * <ul>
 * <li> {@link #sourceType()} is omitted, or one of the source parameter types of the mapping method is assignable to
 * the specified {@link #sourceType()}
 * <li> {@link #targetType()} is omitted, or the result type of the mapping method is assignable to the specified
 * {@link #targetType()}
 * </ul>
 * An &#64;{@link Mapping} annotation on a mapping method has precedence over the configuration for the same source or
 * target property in a global mapping.
 *
 * @author Andreas Gudian
 */
@Retention(RetentionPolicy.CLASS)
@Target({ })
@Documented
public @interface GlobalMapping {

    /**
     * The type that at least one of the source parameter types of a mapping method must be assignable to. If omitted,
     * no restriction on the source types is imposed.
     *
     * @return the source type restriction
     */
    Class<?> sourceType() default Object.class;

    /**
     * The type that the result type of a mapping method must be assignable to. If omitted, no restriction on the result
     * type is imposed.
     *
     * @return the target type restriction
     */
    Class<?> targetType() default Object.class;

    /**
     * The mapping configuration to apply to all matching methods.
     *
     * @return the mapping configuration to be applied to all matching methods
     */
    Mapping[] mappings();
}
