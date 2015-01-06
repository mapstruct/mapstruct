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
import org.mapstruct.ap.util.NativeTypes;

/**
 * Conversion between primitive types such as {@code byte} and wrapper types
 * such as {@link Integer}.
 *
 * @author Gunnar Morling
 */
public class PrimitiveToWrapperConversion extends SimpleConversion {

    private final Class<?> sourceType;
    private final Class<?> targetType;

    public PrimitiveToWrapperConversion(Class<?> sourceType, Class<?> targetType) {
        if ( !sourceType.isPrimitive() ) {
            throw new IllegalArgumentException( sourceType + " is no primitive type." );
        }
        if ( targetType.isPrimitive() ) {
            throw new IllegalArgumentException( targetType + " is no wrapper type." );
        }

        this.sourceType = sourceType;
        this.targetType = NativeTypes.getPrimitiveType( targetType );
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        if ( sourceType == targetType ) {
            return "<SOURCE>";
        }
        else {
            return "(" + targetType.getName() + ") <SOURCE>";
        }
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>." + sourceType.getName() + "Value()";
    }
}
