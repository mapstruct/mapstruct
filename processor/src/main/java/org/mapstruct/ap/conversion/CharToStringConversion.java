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

import org.mapstruct.ap.model.common.ConversionContext;

/**
 * Conversion between {@code char} and {@link String}.
 *
 * @author Gunnar Morling
 */
public class CharToStringConversion extends SimpleConversion {

    @Override
    public String[] getToExpressions(ConversionContext conversionContext) {
        return new String[]{ "String.valueOf( ", " )" };
    }

    @Override
    public String[] getFromExpressions(ConversionContext conversionContext) {
        return new String[]{ "", ".charAt( 0 )" };
    }
}
