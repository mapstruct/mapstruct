/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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

/**
 * Configures the mapping between two bean types.
 * <p>
 * Either {@link #resultType()}, {@link #qualifiedBy()} or {@link #nullValueMappingStrategy()} must be specified.
 * </p>
 *
 * @author Sjaak Derksen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface BeanMapping {

    /**
     * Specifies the result type of the factory method to be used in case several factory methods qualify.
     *
     * @return the resultType to select
     */
    Class<?> resultType() default void.class;

    /**
     * A qualifier can be specified to aid the selection process of a suitable factory method or filtering applicable
     * {@code @}{@link BeforeMapping} / {@code @}{@link AfterMapping} methods. This is useful in case multiple factory
     * method (hand written of internal) qualify and result in an 'Ambiguous factory methods' error.
     * <p>
     * A qualifier is a custom annotation and can be placed on either a hand written mapper class or a method.
     *
     * @return the qualifiers
     * @see BeanMapping#qualifiedByName()
     */
    Class<? extends Annotation>[] qualifiedBy() default { };

    /**
     * Similar to {@link #qualifiedBy()}, but used in combination with {@code @}{@link Named} in case no custom
     * qualifier annotation is defined.
     *
     * @return the qualifiers
     * @see Named
     */
    String[] qualifiedByName() default { };

    /**
     * The strategy to be applied when {@code null} is passed as source value to this bean mapping. If no
     * strategy is configured, the strategy given via {@link MapperConfig#nullValueMappingStrategy()} or
     * {@link Mapper#nullValueMappingStrategy()} will be applied, using {@link NullValueMappingStrategy#RETURN_NULL}
     * by default.
     *
     * @return The strategy to be applied when {@code null} is passed as source value to the methods of this mapping.
     */
    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;

    /**
     * Default ignore all mappings. All mappings have to be defined manually. No automatic mapping will take place. No
     * warning will be issued on missing target properties.
     *
     * @return The ignore strategy (default false).
     *
     * @since 1.3
     */
    boolean ignoreByDefault() default false;

    /**
     * Unmapped source properties to be ignored. This could be used when {@link ReportingPolicy#WARN}
     * or {@link ReportingPolicy#ERROR} is used for {@link Mapper#unmappedSourcePolicy()} or
     * {@link MapperConfig#unmappedSourcePolicy()}. Listed properties will be ignored when composing the unmapped
     * source properties report.
     * <p>
     * <b>NOTE</b>: This does not support ignoring nested source properties
     *
     * @return The source properties that should be ignored when performing a report
     *
     * @since 1.3
     */
    String[] ignoreUnmappedSourceProperties() default {};
}
