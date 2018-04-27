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

import java.sql.Date;
import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Conversion between {@link java.util.Date} and {@link java.sql.Date}.
 *
 * @author Filip Hrisafov
 */
public class DateToSqlDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "new " + date( conversionContext ) + "( <SOURCE>.getTime() )";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.singleton( conversionContext.getTypeFactory().getType( Date.class ) );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>";
    }

    private String date(ConversionContext conversionContext) {
        return conversionContext.getTypeFactory().getType( Date.class ).getReferenceName();
    }
}
