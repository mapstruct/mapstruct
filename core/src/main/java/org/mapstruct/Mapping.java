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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Configures the mapping of one bean attribute.
 *
 * @author Gunnar Morling
 */
public @interface Mapping {

    /**
     * The source name of the configured property as defined by the JavaBeans specification.
     *
     * @return The source name of the configured property.
     */
    String source();

    /**
     * The target name of the configured property as defined by the JavaBeans specification. Defaults to the
     * source name if not given.
     *
     * @return The target name of the configured property.
     */
    String target() default "";

    /**
     * A format string as processable by {@link SimpleDateFormat} if the attribute is mapped from {@code String} to
     * {@link Date} or vice-versa. Will be ignored for all other attribute types.
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     */
    String dateFormat() default "";
}
