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

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.util.TypeUtil;

import static org.mapstruct.ap.conversion.ReverseConversion.reverse;

public class Conversions {

    private TypeUtil typeUtil;
    private final Map<Key, Conversion> conversions = new HashMap<Conversions.Key, Conversion>();
    private final DeclaredType enumType;
    private final DeclaredType stringType;

    public Conversions(Elements elementUtils, Types typeUtils, TypeUtil typeUtil) {
        this.typeUtil = typeUtil;

        this.enumType = typeUtils.getDeclaredType( elementUtils.getTypeElement( Enum.class.getCanonicalName() ) );
        this.stringType = typeUtils.getDeclaredType( elementUtils.getTypeElement( String.class.getCanonicalName() ) );

        registerNumberConversion( byte.class, Byte.class );
        registerNumberConversion( byte.class, short.class );
        registerNumberConversion( byte.class, Short.class );
        registerNumberConversion( byte.class, int.class );
        registerNumberConversion( byte.class, Integer.class );
        registerNumberConversion( byte.class, long.class );
        registerNumberConversion( byte.class, Long.class );
        registerNumberConversion( byte.class, float.class );
        registerNumberConversion( byte.class, Float.class );
        registerNumberConversion( byte.class, double.class );
        registerNumberConversion( byte.class, Double.class );

        registerNumberConversion( Byte.class, short.class );
        registerNumberConversion( Byte.class, Short.class );
        registerNumberConversion( Byte.class, int.class );
        registerNumberConversion( Byte.class, Integer.class );
        registerNumberConversion( Byte.class, long.class );
        registerNumberConversion( Byte.class, Long.class );
        registerNumberConversion( Byte.class, float.class );
        registerNumberConversion( Byte.class, Float.class );
        registerNumberConversion( Byte.class, double.class );
        registerNumberConversion( Byte.class, Double.class );

        registerNumberConversion( short.class, Short.class );
        registerNumberConversion( short.class, int.class );
        registerNumberConversion( short.class, Integer.class );
        registerNumberConversion( short.class, long.class );
        registerNumberConversion( short.class, Long.class );
        registerNumberConversion( short.class, float.class );
        registerNumberConversion( short.class, Float.class );
        registerNumberConversion( short.class, double.class );
        registerNumberConversion( short.class, Double.class );

        registerNumberConversion( Short.class, int.class );
        registerNumberConversion( Short.class, Integer.class );
        registerNumberConversion( Short.class, long.class );
        registerNumberConversion( Short.class, Long.class );
        registerNumberConversion( Short.class, float.class );
        registerNumberConversion( Short.class, Float.class );
        registerNumberConversion( Short.class, double.class );
        registerNumberConversion( Short.class, Double.class );

        registerNumberConversion( int.class, Integer.class );
        registerNumberConversion( int.class, long.class );
        registerNumberConversion( int.class, Long.class );
        registerNumberConversion( int.class, float.class );
        registerNumberConversion( int.class, Float.class );
        registerNumberConversion( int.class, double.class );
        registerNumberConversion( int.class, Double.class );

        registerNumberConversion( Integer.class, long.class );
        registerNumberConversion( Integer.class, Long.class );
        registerNumberConversion( Integer.class, float.class );
        registerNumberConversion( Integer.class, Float.class );
        registerNumberConversion( Integer.class, double.class );
        registerNumberConversion( Integer.class, Double.class );

        registerNumberConversion( long.class, Long.class );
        registerNumberConversion( long.class, float.class );
        registerNumberConversion( long.class, Float.class );
        registerNumberConversion( long.class, double.class );
        registerNumberConversion( long.class, Double.class );

        registerNumberConversion( Long.class, float.class );
        registerNumberConversion( Long.class, Float.class );
        registerNumberConversion( Long.class, double.class );
        registerNumberConversion( Long.class, Double.class );

        registerNumberConversion( float.class, Float.class );
        registerNumberConversion( float.class, double.class );
        registerNumberConversion( float.class, Double.class );

        registerNumberConversion( Float.class, double.class );
        registerNumberConversion( Float.class, Double.class );

        registerNumberConversion( double.class, Double.class );

        register( int.class, String.class, new IntStringConversion() );
        register( Integer.class, String.class, new IntegerStringConversion() );
        register( Enum.class, String.class, new EnumStringConversion() );
    }

    private void registerNumberConversion(Class<?> sourceType, Class<?> targetType) {
        if ( sourceType.isPrimitive() && targetType.isPrimitive() ) {
            register( sourceType, targetType, new PrimitiveToPrimitiveConversion( sourceType ) );
        }
        else if ( sourceType.isPrimitive() && !targetType.isPrimitive() ) {
            register( sourceType, targetType, new PrimitiveToWrapperConversion( sourceType, targetType ) );
        }
        else if ( !sourceType.isPrimitive() && targetType.isPrimitive() ) {
            register( sourceType, targetType, reverse( new PrimitiveToWrapperConversion( targetType, sourceType ) ) );
        }
        else {
            register( sourceType, targetType, new WrapperToWrapperConversion( sourceType, targetType ) );
        }
    }

    private void register(Class<?> sourceType, Class<?> targetType, Conversion conversion) {
        conversions.put( Key.forClasses( sourceType, targetType ), conversion );
        conversions.put( Key.forClasses( targetType, sourceType ), reverse( conversion ) );
    }

    public Conversion getConversion(Type sourceType, Type targetType) {
        if ( sourceType.isEnumType() && targetType.equals( typeUtil.getType( stringType ) ) ) {
            sourceType = typeUtil.getType( enumType );
        }
        else if ( targetType.isEnumType() && sourceType.equals( typeUtil.getType( stringType ) ) ) {
            targetType = typeUtil.getType( enumType );
        }

        return conversions.get( new Key( sourceType, targetType ) );
    }

    private static class Key {
        private final Type sourceType;
        private final Type targetType;

        private static Key forClasses(Class<?> sourceType, Class<?> targetType) {
            return new Key( Type.forClass( sourceType ), Type.forClass( targetType ) );
        }

        private Key(Type sourceType, Type targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public String toString() {
            return "Key [sourceType=" + sourceType + ", targetType="
                + targetType + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                + ( ( sourceType == null ) ? 0 : sourceType.hashCode() );
            result = prime * result
                + ( ( targetType == null ) ? 0 : targetType.hashCode() );
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            Key other = (Key) obj;
            if ( sourceType == null ) {
                if ( other.sourceType != null ) {
                    return false;
                }
            }
            else if ( !sourceType.equals( other.sourceType ) ) {
                return false;
            }
            if ( targetType == null ) {
                if ( other.targetType != null ) {
                    return false;
                }
            }
            else if ( !targetType.equals( other.targetType ) ) {
                return false;
            }
            return true;
        }
    }
}
