/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.math.BigInteger;

import org.mapstruct.ap.util.NativeTypes;

/**
 * Conversion between {@link BigInteger} and wrappers of native number types.
 *
 * @author Gunnar Morling
 */
public class BigIntegerToWrapperConversion extends SimpleConversion {

    private final Class<?> targetType;

    public BigIntegerToWrapperConversion(Class<?> targetType) {
        if ( targetType.isPrimitive() ) {
            throw new IllegalArgumentException( targetType + " is a primitive type." );
        }

        this.targetType = NativeTypes.getPrimitiveType( targetType );
    }

    @Override
    public String getToConversionString(String sourceReference, Context conversionContext) {
        return sourceReference + "." + targetType.getName() + "Value()";
    }

    @Override
    public String getFromConversionString(String targetReference, Context conversionContext) {
        StringBuilder conversion = new StringBuilder( "BigInteger.valueOf( " );

        conversion.append( targetReference );

        if ( targetType == float.class || targetType == double.class ) {
            conversion.append( ".longValue()" );
        }

        conversion.append( " )" );


        return conversion.toString();
    }
}
