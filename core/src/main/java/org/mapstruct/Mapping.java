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
package org.mapstruct;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Configures the mapping of one bean attribute or enum constant.
 * <p>
 * The name of the mapped attribute or constant is to be specified via {@link #target()}. For mapped bean attributes it
 * is assumed by default that the attribute has the same name in the source bean. Alternatively, one of
 * {@link #source()}, {@link #expression()} or {@link #constant()} can be specified to define the property source.
 * <p>
 * In addition, the attributes {@link #dateFormat()} and {@link #qualifiedBy()} may be used to further define the
 * mapping.
 *
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Mapping {

    /**
     * The target name of the configured property as defined by the JavaBeans specification. If used to map an enum
     * constant, the name of the constant member is to be given.
     *
     * @return The target name of the configured property or enum constant
     */
    String target();

    /**
     * The source to use for this mapping. This can either be:
     * <ol>
     * <li>The source name of the configured property as defined by the JavaBeans specification.</li>
     * <li>When used to map an enum constant, the name of the constant member is to be given.</li>
     * </ol>
     * Either this attribute or {@link #constant()} or {@link #expression()} may be specified for a given mapping.
     *
     * @return The source name of the configured property or enum constant.
     */
    String source() default "";

    /**
     * A format string as processable by {@link SimpleDateFormat} if the attribute is mapped from {@code String} to
     * {@link Date} or vice-versa. Will be ignored for all other attribute types and when mapping enum constants.
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     */
    String dateFormat() default "";

    /**
     * A constant {@link String} based on which the specified target property is to be set. If the designated target
     * property is not of type {@code String}, the value will be converted by applying a matching conversion method or
     * built-in conversion.
     * <p>
     * Either this attribute or {@link #source()} or {@link #expression()} may be specified for a given mapping.
     *
     * @return A constant {@code String} constant specifying the value for the designated target property
     */
    String constant() default "";

    /**
     * An expression {@link String} based on which the specified target property is to be set. The format is determined
     * by a type of expression. For instance:
     * {@code expression = "java(new org.example.TimeAndFormat( s.getTime(), s.getFormat() ))")} will insert the java
     * expression in the designated {@link #target()} property.
     * <p>
     * Either this attribute or {@link #source()} or {@link #constant()} may be specified for a given mapping.
     *
     * @return A constant {@code String} constant specifying the value for the designated target property
     */
    String expression() default "";

    /**
     * Whether the property specified via {@link #target()} should be ignored by the generated mapping method or not.
     * This can be useful when certain attributes should not be propagated from source or target or when properties in
     * the target object are populated using a decorator and thus would be reported as unmapped target property by
     * default.
     *
     * @return {@code true} if the given property should be ignored, {@code false} otherwise
     */
    boolean ignore() default false;

    /**
     * A qualifier can be specified to aid the selection process of a suitable mapper. This is useful in case multiple
     * mapping methods (hand written or generated) qualify and thus would result in an 'Ambiguous mapping methods found'
     * error. A qualifier is a custom annotation and can be placed on a hand written mapper class or a method.
     *
     * @return the qualifiers
     */
    Class<? extends Annotation>[] qualifiedBy() default { };
}
