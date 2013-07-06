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

import org.mapstruct.ap.model.Type;

/**
 * Conversion between {@link BigInteger} and native number types.
 *
 * @author Gunnar Morling
 */
public class BigIntegerToPrimitiveConversion implements Conversion {

    private final Class<?> targetType;

    public BigIntegerToPrimitiveConversion(Class<?> targetType) {
        if ( !targetType.isPrimitive() ) {
            throw new IllegalArgumentException( targetType + " is no primitive type." );
        }

        this.targetType = targetType;
    }

    @Override
    public String to(String sourcePropertyAccessor, Type type) {
        return sourcePropertyAccessor + "." + targetType.getName() + "Value()";
    }

    @Override
    public String from(String targetPropertyAccessor, Type type) {
        StringBuilder conversion = new StringBuilder( "BigInteger.valueOf( " );

        if ( targetType == float.class || targetType == double.class ) {
            conversion.append( "(long) " );
        }

        conversion.append( targetPropertyAccessor ).append( " )" );

        return conversion.toString();
    }
}
