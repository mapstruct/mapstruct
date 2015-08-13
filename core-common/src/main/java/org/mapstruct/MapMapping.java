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

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Configures the mapping between two map types, e.g. {@code Map<String, String>} and {@code Map<Long, Date>}.
 *
 * <p>Note: at least one element needs to be specified</p>
 *
 * @author Gunnar Morling
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface MapMapping {

    /**
     * A format string as processable by {@link SimpleDateFormat} if the annotated method maps from a map with key type
     * {@code String} to an map with key type {@link Date} or vice-versa. Will be ignored for all other key types.
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     */
    String keyDateFormat() default "";

    /**
     * A format string as processable by {@link SimpleDateFormat} if the annotated method maps from a map with value
     * type {@code String} to an map with value type {@link Date} or vice-versa. Will be ignored for all other value
     * types.
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     */
    String valueDateFormat() default "";

    /**
     * A key value qualifier can be specified to aid the selection process of a suitable mapper. This is useful in
     * case multiple mappers (hand written of internal) qualify and result in an 'Ambiguous mapping methods found'
     * error.
     *
     * A qualifier is a custom annotation and can be placed on either a hand written mapper class or a method.
     *
     * @return the qualifiers
     */
    Class<? extends Annotation>[] keyQualifiedBy() default { };


    /**
     * A value qualifier can be specified to aid the selection process of a suitable mapper for the values in the map.
     * This is useful in case multiple mappers (hand written of internal) qualify and result in an 'Ambiguous mapping
     * methods found' error.
     * <p>
     * A qualifier is a custom annotation and can be placed on either a hand written mapper class or a method.
     *
     * @return the qualifiers
     */
    Class<? extends Annotation>[] valueQualifiedBy() default { };

    /**
     * Specifies the type of the key to be used in the result of the mapping method in case multiple mapping
     * methods qualify.
     *
     *
     * @return the resultType to select
     */
    Class<?> keyTargetType() default void.class;

    /**
     * Specifies the type of the value to be used in the result of the mapping method in case multiple mapping
     * methods qualify.
     *
     *
     * @return the resultType to select
     */
    Class<?> valueTargetType() default void.class;


    /**
     * The strategy to be applied when {@code null} is passed as source value to this map mapping. If no
     * strategy is configured, the strategy given via {@link MapperConfig#nullValueMappingStrategy()} or
     * {@link Mapper#nullValueMappingStrategy()} will be applied, using {@link NullValueMappingStrategy#RETURN_NULL}
     * by default.
     *
     * @return The strategy to be applied when {@code null} is passed as source value to the methods of this mapping.
     */
    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;
}
