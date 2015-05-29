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

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.util.NativeTypes;

/**
 * Conversion between wrapper types such as {@link Integer} or {@link Long}.
 *
 * @author Gunnar Morling
 */
public class WrapperToWrapperConversion extends SimpleConversion {

    private final Class<?> sourceType;
    private final Class<?> targetType;

    public WrapperToWrapperConversion(Class<?> sourceType, Class<?> targetType) {
        if ( sourceType.isPrimitive() ) {
            throw new IllegalArgumentException( sourceType + " is no wrapper type." );
        }
        if ( targetType.isPrimitive() ) {
            throw new IllegalArgumentException( targetType + " is no wrapper type." );
        }

        this.sourceType = NativeTypes.getPrimitiveType( sourceType );
        this.targetType = NativeTypes.getPrimitiveType( targetType );
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        if ( sourceType == targetType ) {
            return "<SOURCE>";
        }
        else {
            return "<SOURCE>." + targetType.getName() + "Value()";
        }
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        if ( sourceType == targetType ) {
            return "<SOURCE>";
        }
        else {
            return "<SOURCE>." + sourceType.getName() + "Value()";
        }
    }
}
