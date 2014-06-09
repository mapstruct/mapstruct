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

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Configures the mapping of one bean attribute or enum constant.
 *
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Repeatable(Mappings.class)
public @interface Mapping {

    /**
     * The source to use for this Mapping. This can either be:
     * <ol>
     * <li>The source name of the configured property as defined by the JavaBeans specification.</li>
     * <li>When used to map an enum constant, the name of the constant member is to be given<./li>.
     * </ol>
     * Either this attribute or {@link #expression()} may be specified for a given mapping, but not both at the same
     * time.
     *
     * @return The source name of the configured property or enum constant.
     */
    String source() default "";

    /**
     * The target name of the configured property as defined by the JavaBeans specification. Defaults to the source name
     * if not given. If used to map an enum constant, the name of the constant member is to be given.
     *
     * @return The target name of the configured property or enum constant
     */
    String target() default "";

    /**
     * A format string as processable by {@link SimpleDateFormat} if the attribute is mapped from {@code String} to
     * {@link Date} or vice-versa. Will be ignored for all other attribute types and when mapping enum constants.
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     */
    String dateFormat() default "";

    /**
     * A constant {@link String} expression based on which the specified target property is to be set. If the designated
     * target property is not of type {@code String}, the value will be converted by applying a matching conversion
     * method or built-in conversion.
     * <p>
     * Either this attribute or {@link #source()} may be specified for a given mapping, but not both at the same time.
     * If this attribute is given, the target property must be specified via {@link #target()}.
     *
     * @return A constant {@code String} expression specifying the value for the designated target prperty
     */
    String expression() default "";
}
