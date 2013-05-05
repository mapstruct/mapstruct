/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

        register( int.class, Long.class, new IntLongConversion() );
        register( int.class, String.class, new IntStringConversion() );
        register( Integer.class, String.class, new IntegerStringConversion() );
        register( Enum.class, String.class, new EnumStringConversion() );
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
