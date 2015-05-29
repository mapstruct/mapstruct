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
package org.mapstruct.ap.internal.model.common;

import java.util.Date;

/**
 * Context object passed to conversion providers and built-in methods.
 *
 * @author Gunnar Morling
 */
public interface ConversionContext {

    /**
     * Returns the target type of this conversion.
     *
     * @return The target type of this conversion.
     */
    Type getTargetType();

    /**
     * Returns the date format if this conversion or built-in method is from String to a date type (e.g. {@link Date})
     * or vice versa.
     *
     * @return The date format if this conversion or built-in method is from String to a date type. {@code null} is
     *         returned for other types or if not given.
     */
    String getDateFormat();

    TypeFactory getTypeFactory();

}
