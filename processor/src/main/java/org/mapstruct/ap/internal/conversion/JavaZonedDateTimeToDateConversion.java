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

import java.util.Date;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.date;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.zoneId;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.zonedDateTime;
import static org.mapstruct.ap.internal.util.JavaTimeConstants.ZONED_DATE_TIME_FQN;
import static org.mapstruct.ap.internal.util.JavaTimeConstants.ZONE_ID_FQN;

/**
 * SimpleConversion for mapping {@link java.time.ZonedDateTime} to
 * {@link java.util.Date} and vice versa.
 */

public class JavaZonedDateTimeToDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return date( conversionContext ) + ".from( <SOURCE>.toInstant() )";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( Date.class )
        );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return zonedDateTime( conversionContext )
            + ".ofInstant( <SOURCE>.toInstant(), "
            + zoneId( conversionContext )
            + ".systemDefault() )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( ZONED_DATE_TIME_FQN ),
            conversionContext.getTypeFactory().getType( ZONE_ID_FQN )
        );
    }
}
