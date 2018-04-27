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
package org.mapstruct.ap.internal.conversion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/**
 * SimpleConversion for mapping {@link java.time.LocalDate} to
 * {@link java.util.Date} and vice versa.
 */
public class JavaLocalDateToDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return date( conversionContext )
            + ".from( <SOURCE>.atStartOfDay( "
            + zoneOffset( conversionContext )
            + ".UTC ).toInstant() )";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( Date.class ),
            conversionContext.getTypeFactory().getType( ZoneOffset.class )
        );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return localDateTime( conversionContext )
            + ".ofInstant( <SOURCE>.toInstant(), "
            + zoneOffset( conversionContext )
            + ".UTC ).toLocalDate()";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( LocalDateTime.class ),
            conversionContext.getTypeFactory().getType( ZoneId.class )
        );
    }

    private String date(ConversionContext conversionContext) {
        return conversionContext.getTypeFactory().getType( Date.class ).getReferenceName();
    }

    private String zoneOffset(ConversionContext conversionContext) {
        return conversionContext.getTypeFactory().getType( ZoneOffset.class ).getReferenceName();
    }

    private String localDateTime(ConversionContext conversionContext) {
        return conversionContext.getTypeFactory().getType( LocalDateTime.class ).getReferenceName();
    }
}
