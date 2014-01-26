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
package org.mapstruct.ap.conversion;

import java.util.Date;

import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;

/**
 * Implementations create inline {@link TypeConversion}s such as
 *
 * <ul>
 * <li>{@code (long)source},</li>
 * <li>{@code Integer.valueOf(source)} or</li>
 * <li>{@code new SimpleDateFormat().format( source )}.</li>
 * </ul>
 *
 * @author Gunnar Morling
 */
public interface ConversionProvider {

    /**
     * Creates the conversion from source to target of a property mapping.
     *
     * @param sourceReference A reference to the source object, e.g.
     * {@code beanName.getFoo()}.
     * @param conversionContext Context providing optional information required for creating
     * the conversion.
     *
     * @return A conversion from source to target.
     */
    TypeConversion to(String sourceReference, Context conversionContext);

    /**
     * Creates the conversion from target to source of a property mapping.
     *
     * @param targetReference A reference to the targetReference object, e.g.
     * {@code beanName.getFoo()}.
     * @param conversionContext Context providing optional information required for creating
     * the conversion.
     *
     * @return A conversion from target to source.
     */
    TypeConversion from(String targetReference, Context conversionContext);

    /**
     * Context object passed to conversion providers.
     *
     * @author Gunnar Morling
     */
    public interface Context {

        /**
         * Returns the target type of this conversion.
         *
         * @return The target type of this conversion.
         */
        Type getTargetType();

        /**
         * Returns the date format if this conversion is from String to
         * {@link Date} or vice versa. Returns {@code null} for other types or
         * if not given.
         *
         * @return The date format if this conversion.
         */
        String getDateFormat();

        TypeFactory getTypeFactory();
    }
}
