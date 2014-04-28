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

import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Type;

import java.util.Collections;

/**
 * Implementation of {@link org.mapstruct.ap.conversion.ConversionProvider} mapping Joda Types
 * <ul>
 * <li>org.joda.time.DateTime</li>
 * <li>org.joda.time.LocalDateTime</li>
 * <li>org.joda.time.LocalDate</li>
 * </ul>
 * to java.util.Date by invoking org.joda.time.base.AbstractInstant#toDate().
 * Backward conversion is done.
 */
public class JodaTimeToDateConversion implements ConversionProvider {

    @Override
    public TypeConversion to(String sourceReference, ConversionContext conversionContext) {
        return new TypeConversion(
                        Collections.<Type>emptySet(),
                        Collections.<Type>emptyList(),
                        sourceReference + ".toDate()" );
    }

    @Override
    public TypeConversion from(String targetReference, ConversionContext conversionContext) {
        return new TypeConversion(
                        Collections.<Type>emptySet(),
                        Collections.<Type>emptyList(),
                        "new " + conversionContext.getTargetType().getFullyQualifiedName() + "( " + targetReference
                                        + " )" );
    }
}
