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

import java.math.BigDecimal;
import java.util.Set;

import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Type;

import static org.mapstruct.ap.util.Collections.asSet;

/**
 * Conversion between {@link BigDecimal} and {@link String}.
 *
 * @author Gunnar Morling
 */
public class BigDecimalToStringConversion extends SimpleConversion {

    @Override
    public String[] getToExpressions(ConversionContext conversionContext) {
        return new String[]{ "", ".toString()" };
    }

    @Override
    public String[] getFromExpressions(ConversionContext conversionContext) {
        return new String[]{ "new BigDecimal( ", " )" };
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( BigDecimal.class ) );
    }
}
