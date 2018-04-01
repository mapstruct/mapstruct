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
package org.mapstruct.ap.internal.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.lang.model.type.TypeKind;

/**
 * Provides functionality around the Java primitive data types and their wrapper
 * types.
 *
 * @author Gunnar Morling
 */
public class NativeTypes {

    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPES;
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPES;
    private static final Set<Class<?>> NUMBER_TYPES = new HashSet<Class<?>>();
    private static final Map<String, TypeKind> WRAPPER_NAME_TO_PRIMITIVE_TYPES;

    private static final Pattern PTRN_HEX = Pattern.compile( "^0[x|X].*" );
    private static final Pattern PTRN_OCT = Pattern.compile( "^0_*[0-7].*" );
    private static final Pattern PTRN_BIN = Pattern.compile( "^0[b|B].*" );
    private static final Pattern PTRN_FLOAT_DEC_ZERO = Pattern.compile( "^[^eE]*[1-9].*[eE]?.*" );
    private static final Pattern PTRN_FLOAT_HEX_ZERO = Pattern.compile( "^[^pP]*[1-9a-fA-F].*[pP]?.*" );

    private static final Pattern PTRN_SIGN = Pattern.compile( "^[\\+|-]" );

    private static final Pattern PTRN_LONG = Pattern.compile( "[l|L]$" );
    private static final Pattern PTRN_FLOAT = Pattern.compile( "[f|F]$" );
    private static final Pattern PTRN_DOUBLE = Pattern.compile( "[d|D]$" );

    private static final Pattern PTRN_FAULTY_UNDERSCORE_INT = Pattern.compile( "^_|_$|-_|_-|\\+_|_\\+" );
    private static final Pattern PTRN_FAULTY_UNDERSCORE_FLOAT = Pattern.compile( "^_|_$|-_|_-|\\+_|_\\+|\\._|_\\." );
    private static final Pattern PTRN_FAULTY_DEC_UNDERSCORE_FLOAT = Pattern.compile( "_e|_E|e_|E_" );
    private static final Pattern PTRN_FAULTY_HEX_UNDERSCORE_FLOAT = Pattern.compile( "_p|_P|p_|P_" );

    private static final Map<TypeKind, NumberFormatValidator> VALIDATORS = initValidators();

    private interface NumberFormatValidator {

        boolean validate(boolean isPrimitive, String s);
    }

    private abstract static class NumberRepresentation {

        int radix;
        String val;
        boolean isIntegralType;
        boolean isLong;
        boolean isFloat;
        boolean isPrimitive;

        NumberRepresentation(String in, boolean isIntegralType, boolean isLong, boolean isFloat, boolean isPrimitive) {
            this.isLong = isLong;
            this.isFloat = isFloat;
            this.isIntegralType = isIntegralType;
            this.isPrimitive = isPrimitive;

            String valWithoutSign;
            boolean isNegative = in.startsWith( "-" );
            boolean hasSign = PTRN_SIGN.matcher( in ).find();
            if ( hasSign ) {
                valWithoutSign = in.substring( 1 );
            }
            else {
                valWithoutSign = in;
            }
            if ( PTRN_HEX.matcher( valWithoutSign ).matches() ) {
                // hex
                radix = 16;
                val = (isNegative ? "-" : "") + valWithoutSign.substring( 2 );
            }
            else if ( PTRN_BIN.matcher( valWithoutSign ).matches() ) {
                // binary
                radix = 2;
                val = (isNegative ? "-" : "") + valWithoutSign.substring( 2 );
            }
            else if ( PTRN_OCT.matcher( valWithoutSign ).matches() ) {
                // octal
                radix = 8;
                val = (isNegative ? "-" : "") + valWithoutSign.substring( 1 );
            }
            else {
                // decimal
                radix = 10;
                val = (isNegative ? "-" : "") + valWithoutSign;
            }
        }

        abstract boolean parse(String val, int radix);

        boolean validate() {
            try {
                strip();
                return parse( val, radix );
            }
            catch ( NumberFormatException ex ) {
                return false;
            }
        }

        void strip() {
            if ( isIntegralType ) {
                removeAndValidateIntegerLiteralSuffix();
                removeAndValidateIntegerLiteralUnderscore();
            }
            else {
                removeAndValidateFloatingPointLiteralSuffix();
                removeAndValidateFloatingPointLiteralUnderscore();
            }
        }

        /**
         * remove java7+ underscores from the input
         */
        void removeAndValidateIntegerLiteralUnderscore() {
            if ( PTRN_FAULTY_UNDERSCORE_INT.matcher( val ).find() ) {
                throw new NumberFormatException("Improperly placed underscores");
            }
            else {
                val = val.replace( "_", "" );
            }
        }

        /**
         * remove java7+ underscores from the input
         */
        void removeAndValidateFloatingPointLiteralUnderscore() {
            boolean isHex = radix == 16;
            if ( PTRN_FAULTY_UNDERSCORE_FLOAT.matcher( val ).find()
                || !isHex && PTRN_FAULTY_DEC_UNDERSCORE_FLOAT.matcher( val ).find()
                || isHex && PTRN_FAULTY_HEX_UNDERSCORE_FLOAT.matcher( val ).find() ) {
                throw new NumberFormatException("Improperly placed underscores");
            }
            else {
                val = val.replace( "_", "" );
            }
        }

        /**
         *
         */
        void removeAndValidateIntegerLiteralSuffix() {
            boolean endsWithLSuffix = PTRN_LONG.matcher( val ).find();
            // error handling
            if (endsWithLSuffix && !isLong) {
                throw new NumberFormatException("L/l not allowed for non-long types");
            }
            if (!isPrimitive && !endsWithLSuffix && isLong)  {
                throw new NumberFormatException("L/l mandatory for boxed long");
            }
            // remove suffix
            if ( endsWithLSuffix ) {
                val = val.substring( 0, val.length() - 1 );
            }

        }

        /**
         * Double suffix forbidden for float.
         *
         * @param isFloat
         */
        void removeAndValidateFloatingPointLiteralSuffix() {
            boolean endsWithLSuffix = PTRN_LONG.matcher( val ).find();
            boolean endsWithFSuffix = PTRN_FLOAT.matcher( val ).find();
            boolean endsWithDSuffix = PTRN_DOUBLE.matcher( val ).find();
            // error handling
            if ( isFloat && endsWithDSuffix ) {
                throw new NumberFormatException("Assiging double to a float");
            }
            // remove suffix
            if ( endsWithLSuffix || endsWithFSuffix || endsWithDSuffix ) {
                val = val.substring( 0, val.length() - 1 );
            }
        }

        boolean floatHasBecomeZero(float parsed) {
            if ( parsed == 0f ) {
                return floatHasBecomeZero();
            }
            else {
                return false;
            }
        }

        boolean doubleHasBecomeZero(double parsed) {
            if ( parsed == 0d ) {
                return floatHasBecomeZero();
            }
            else {
                return false;
            }
        }

        private boolean floatHasBecomeZero() {
            if ( radix == 10 ) {
                // decimal, should be at least some number before exponent (eE) unequal to 0.
                return PTRN_FLOAT_DEC_ZERO.matcher( val ).matches();
            }
            else {
                // hex, should be at least some number before exponent (pP) unequal to 0.
                return PTRN_FLOAT_HEX_ZERO.matcher( val ).matches();
            }
        }
    }

    private NativeTypes() {
    }

    static {
        Map<Class<?>, Class<?>> tmp = new HashMap<Class<?>, Class<?>>();
        tmp.put( Byte.class, byte.class );
        tmp.put( Short.class, short.class );
        tmp.put( Integer.class, int.class );
        tmp.put( Long.class, long.class );
        tmp.put( Float.class, float.class );
        tmp.put( Double.class, double.class );
        tmp.put( Boolean.class, boolean.class );
        tmp.put( Character.class, char.class );

        WRAPPER_TO_PRIMITIVE_TYPES = Collections.unmodifiableMap( tmp );

        tmp = new HashMap<Class<?>, Class<?>>();
        tmp.put( byte.class, Byte.class );
        tmp.put( short.class, Short.class );
        tmp.put( int.class, Integer.class );
        tmp.put( long.class, Long.class );
        tmp.put( float.class, Float.class );
        tmp.put( double.class, Double.class );
        tmp.put( boolean.class, Boolean.class );
        tmp.put( char.class, Character.class );

        PRIMITIVE_TO_WRAPPER_TYPES = Collections.unmodifiableMap( tmp );

        NUMBER_TYPES.add( byte.class );
        NUMBER_TYPES.add( short.class );
        NUMBER_TYPES.add( int.class );
        NUMBER_TYPES.add( long.class );
        NUMBER_TYPES.add( float.class );
        NUMBER_TYPES.add( double.class );
        NUMBER_TYPES.add( Byte.class );
        NUMBER_TYPES.add( Short.class );
        NUMBER_TYPES.add( Integer.class );
        NUMBER_TYPES.add( Long.class );
        NUMBER_TYPES.add( Float.class );
        NUMBER_TYPES.add( Double.class );
        NUMBER_TYPES.add( BigInteger.class );
        NUMBER_TYPES.add( BigDecimal.class );

        Map<String, TypeKind> tmp2 = new HashMap<String, TypeKind>();
        tmp2.put( Boolean.class.getName(), TypeKind.BOOLEAN );
        tmp2.put( Byte.class.getName(), TypeKind.BYTE );
        tmp2.put( Character.class.getName(), TypeKind.CHAR );
        tmp2.put( Double.class.getName(), TypeKind.DOUBLE );
        tmp2.put( Float.class.getName(), TypeKind.FLOAT );
        tmp2.put( Integer.class.getName(), TypeKind.INT );
        tmp2.put( Long.class.getName(), TypeKind.LONG );
        tmp2.put( Short.class.getName(), TypeKind.SHORT );

        WRAPPER_NAME_TO_PRIMITIVE_TYPES = Collections.unmodifiableMap( tmp2 );

    }

    public static Class<?> getWrapperType(Class<?> clazz) {
        if ( !clazz.isPrimitive() ) {
            throw new IllegalArgumentException( clazz + " is no primitive type." );
        }

        return PRIMITIVE_TO_WRAPPER_TYPES.get( clazz );
    }

    public static Class<?> getPrimitiveType(Class<?> clazz) {
        if ( clazz.isPrimitive() ) {
            throw new IllegalArgumentException( clazz + " is no wrapper type." );
        }

        return WRAPPER_TO_PRIMITIVE_TYPES.get( clazz );
    }

    public static boolean isWrapped(String fullyQualifiedName) {
        return WRAPPER_NAME_TO_PRIMITIVE_TYPES.containsKey( fullyQualifiedName );
    }

    public static TypeKind getWrapperKind(String fullyQualifiedName) {
        return WRAPPER_NAME_TO_PRIMITIVE_TYPES.get( fullyQualifiedName );
    }

    public static boolean isNumber(Class<?> clazz) {
        if ( clazz == null ) {
            return false;
        }
        else {
            return NUMBER_TYPES.contains( clazz );
        }
    }

    public static boolean isStringAssignable(TypeKind kind, boolean isPrimitive, String in) {
        NumberFormatValidator validator = VALIDATORS.get( kind );
        return validator != null && validator.validate( isPrimitive, in );
    }

    private static Map<TypeKind, NumberFormatValidator> initValidators() {
        Map<TypeKind, NumberFormatValidator> result = new HashMap<TypeKind, NumberFormatValidator>();
        result.put( TypeKind.BOOLEAN, new NumberFormatValidator() {
            @Override
            public boolean validate(boolean isPrimitive, String s) {
                return "true".equals( s ) || "false".equals( s );
            }
        } );
        result.put( TypeKind.CHAR, new NumberFormatValidator() {
            @Override
            public boolean validate(boolean isPrimitive, String s) {
                return s.length() == 3 && s.startsWith( "'" ) && s.endsWith( "'" );
            }
        } );
        result.put( TypeKind.BYTE, new NumberFormatValidator() {
            @Override
            public boolean validate(boolean isPrimitive, String s) {
                NumberRepresentation br = new NumberRepresentation( s, true, false, false, isPrimitive ) {

                    @Override
                    boolean parse(String val, int radix) {
                        Byte.parseByte( val, radix );
                        return true;
                    }
                };
                return br.validate();
            }
        } );
        result.put( TypeKind.DOUBLE, new NumberFormatValidator() {
            @Override
            public boolean validate(boolean isPrimitive, String s) {
                NumberRepresentation br = new NumberRepresentation( s, false, false, false, isPrimitive ) {

                    @Override
                    boolean parse(String val, int radix) {
                        Double d = Double.parseDouble( radix == 16 ? "0x" + val : val );
                        return !d.isInfinite() && !doubleHasBecomeZero( d );
                    }
                };
                return br.validate();
            }
        } );
        result.put( TypeKind.FLOAT, new NumberFormatValidator() {
            @Override
            public boolean validate(boolean isPrimitive, String s) {

                NumberRepresentation br = new NumberRepresentation( s, false, false, true, isPrimitive ) {
                    @Override
                    boolean parse(String val, int radix) {
                        Float f = Float.parseFloat( radix == 16 ? "0x" + val : val );
                        return !f.isInfinite() && !floatHasBecomeZero( f );
                    }
                };
                return br.validate();
            }
        } );
        result.put( TypeKind.INT, new NumberFormatValidator() {
            @Override
            public boolean validate(boolean isPrimitive, String s) {
                NumberRepresentation br = new NumberRepresentation( s, true, false, false, isPrimitive ) {

                    @Override
                    boolean parse(String val, int radix) {
                        if ( radix == 10 ) {
                            // when decimal: treat like signed
                            Integer.parseInt( val, radix );
                            return true;
                        }
                        else {
                            // when binary, octal or hex: treat like unsigned
                            return new BigInteger( val, radix ).bitLength() <= 32;
                        }
                    }
                };
                return br.validate();
            }
        } );
        result.put( TypeKind.LONG, new NumberFormatValidator() {
            @Override
            public boolean validate(boolean isPrimitive, String s) {
                NumberRepresentation br = new NumberRepresentation( s, true, true, false, isPrimitive ) {

                    @Override
                    boolean parse(String val, int radix) {
                        if ( radix == 10 ) {
                            // when decimal: treat like signed
                            Long.parseLong( val, radix );
                            return true;
                        }
                        else {
                            // when binary, octal or hex: treat like unsigned
                            return new BigInteger( val, radix ).bitLength() <= 64;
                        }
                    }
                };
                return br.validate();
            }
        } );
        result.put( TypeKind.SHORT, new NumberFormatValidator() {
            @Override
            public boolean validate(boolean isPrimitive, String s) {
                NumberRepresentation br = new NumberRepresentation( s, true, false, false, isPrimitive ) {

                    @Override
                    boolean parse(String val, int radix) {
                        Short.parseShort( val, radix );
                        return true;
                    }
                };
                return br.validate();
            }
        } );
        return result;
    }

}
