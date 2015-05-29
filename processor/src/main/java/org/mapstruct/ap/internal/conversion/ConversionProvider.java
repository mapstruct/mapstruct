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
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.TypeConversion;
import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;

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
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     *
     * @return A conversion from source to target.
     */
    Assignment to(ConversionContext conversionContext);

    /**
     * Creates the conversion from target to source of a property mapping.
     *
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     *
     * @return A conversion from target to source.
     */
    Assignment from(ConversionContext conversionContext);
}
