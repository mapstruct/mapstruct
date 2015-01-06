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
package org.mapstruct.ap.conversion;

import org.mapstruct.ap.model.common.ConversionContext;

/**
 * SimpleConversion for mapping {@link java.time.LocalDateTime} to
 * {@link java.util.Date} and vice versa.
 */
public class JavaLocalDateTimeToDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "java.util.Date.from( <SOURCE>.toInstant( java.time.ZoneOffset.UTC ) )";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "java.time.LocalDateTime.ofInstant( <SOURCE>.toInstant(), java.time.ZoneId.systemDefault() )";
    }
}
