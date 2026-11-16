/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.lang.model.type.TypeKind;

/**
 * Provides functionality around the Java primitive data types and their wrapper types. They are considered native.
 *
 * @author Gunnar Morling
 */
public class NativeTypes {

    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPES;
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPES;
    private static final Set<Class<?>> NUMBER_TYPES = new HashSet<>();
    private static final Map<TypeKind, String> TYPE_KIND_NAME = new EnumMap<>( TypeKind.class );
    private static final Map<String, LiteralAnalyzer> ANALYZERS;

    private static final Map<String, Integer> NARROWING_LUT;

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

    private interface LiteralAnalyzer {

        void validate(String s);

        Class<?> getLiteral();

    }

    private abstract static class NumberRepresentation {

        int radix;
        String val;
        boolean isIntegralType;
        boolean isLong;
        boolean isFloat;

        NumberRepresentation(String in, boolean isIntegralType, boolean isLong, boolean isFloat) {
            this.isLong = isLong;
            this.isFloat = isFloat;
            this.isIntegralType = isIntegralType;

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

        abstract void parse(String val, int radix);

        void validate() {
            strip();
            parse( val, radix );
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
                throw new NumberFormatException( "improperly placed underscores" );
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
                throw new NumberFormatException( "improperly placed underscores" );
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
            if ( endsWithLSuffix && !isLong ) {
                throw new NumberFormatException( "L/l not allowed for non-long types" );
            }
            if ( !endsWithLSuffix && isLong ) {
                throw new NumberFormatException( "L/l mandatory for long types" );
            }
            // remove suffix
            if ( endsWithLSuffix ) {
                val = val.substring( 0, val.length() - 1 );
            }

        }

        /**
         * Double suffix forbidden for float.
         *
         */
        void removeAndValidateFloatingPointLiteralSuffix() {
            boolean endsWithLSuffix = PTRN_LONG.matcher( val ).find();
            boolean endsWithFSuffix = PTRN_FLOAT.matcher( val ).find();
            boolean endsWithDSuffix = PTRN_DOUBLE.matcher( val ).find();
            // error handling
            if ( isFloat && endsWithDSuffix ) {
                throw new NumberFormatException( "Assigning double to a float" );
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

   private static class BooleanAnalyzer implements LiteralAnalyzer {

        @Override
        public void validate(String s) {
            if ( !( "true".equals( s ) || "false".equals( s ) ) ) {
                throw new IllegalArgumentException("only 'true' or 'false' are supported");
            }
        }

        @Override
        public Class<?> getLiteral() {
            return boolean.class;
        }
    }

    private static class CharAnalyzer implements LiteralAnalyzer {

        @Override
        public void validate(String s) {
            if ( !(s.length() == 3 && s.startsWith( "'" ) && s.endsWith( "'" )) ) {
                throw new NumberFormatException( "invalid character literal" );
            }
        }

        @Override
        public Class<?> getLiteral() {
            return char.class;
        }
    }

    private static class ByteAnalyzer implements LiteralAnalyzer {

        @Override
        public void validate(String s) {
            NumberRepresentation br = new NumberRepresentation( s, true, false, false ) {

                @Override
                void parse(String val, int radix) {
                    Byte.parseByte( val, radix );
                }
            };
            br.validate();
        }

        @Override
        public Class<?> getLiteral() {
            return int.class;
        }
    }

    private static class DoubleAnalyzer implements LiteralAnalyzer {

        @Override
        public void validate(String s) {
            NumberRepresentation br = new NumberRepresentation( s, false, false, false ) {

                @Override
                void parse(String val, int radix) {
                    double d = Double.parseDouble( radix == 16 ? "0x" + val : val );
                    if ( doubleHasBecomeZero( d  ) ) {
                        throw new NumberFormatException( "floating point number too small" );
                    }
                    if ( Double.isInfinite( d ) ) {
                        throw new NumberFormatException( "infinitive is not allowed" );
                    }
                }
            };
            br.validate();
        }

        @Override
        public Class<?> getLiteral() {
            return double.class;
        }

    }

    private static class FloatAnalyzer implements LiteralAnalyzer {

        @Override
        public void validate(String s) {

            NumberRepresentation br = new NumberRepresentation( s, false, false, true ) {
                @Override
                void parse(String val, int radix) {
                    float f = Float.parseFloat( radix == 16 ? "0x" + val : val );
                    if ( doubleHasBecomeZero( f  ) ) {
                        throw new NumberFormatException( "floating point number too small" );
                    }
                    if ( Float.isInfinite( f ) ) {
                        throw new NumberFormatException( "infinitive is not allowed" );
                    }
                }
            };
            br.validate();
        }

        @Override
        public Class<?> getLiteral() {
            return float.class;
        }
    }

    private static class IntAnalyzer implements LiteralAnalyzer {

        @Override
        public void validate(String s) {
            NumberRepresentation br = new NumberRepresentation( s, true, false, false ) {

                @Override
                void parse(String val, int radix) {
                    if ( radix == 10 ) {
                        // when decimal: treat like signed
                        Integer.parseInt( val, radix );
                    }
                    else if ( new BigInteger( val, radix ).bitLength() > 32 ) {
                        throw new NumberFormatException( "integer number too large" );
                    }
                }
            };
            br.validate();
        }

        @Override
        public Class<?> getLiteral() {
            return int.class;
        }
    }

    private static class LongAnalyzer implements LiteralAnalyzer {

        @Override
        public void validate(String s) {
            NumberRepresentation br = new NumberRepresentation( s, true, true, false ) {

                @Override
                void parse(String val, int radix) {
                    if ( radix == 10 ) {
                        // when decimal: treat like signed
                        Long.parseLong( val, radix );
                    }
                    else if ( new BigInteger( val, radix ).bitLength() > 64 ) {
                        throw new NumberFormatException( "integer number too large" );
                    }
                }
            };
            br.validate();
        }

        @Override
        public Class<?> getLiteral() {
            return long.class;
        }
    }

    private static class ShortAnalyzer implements LiteralAnalyzer {

        @Override
        public void validate(String s) {
            NumberRepresentation br = new NumberRepresentation( s, true, false, false ) {

                @Override
                void parse(String val, int radix) {
                    Short.parseShort( val, radix );
                }
            };
            br.validate();
        }

        @Override
        public Class<?> getLiteral() {
            return int.class;
        }
    }

    private NativeTypes() {
    }

    static {
        Map<Class<?>, Class<?>> tmp = new HashMap<>();
        tmp.put( Byte.class, byte.class );
        tmp.put( Short.class, short.class );
        tmp.put( Integer.class, int.class );
        tmp.put( Long.class, long.class );
        tmp.put( Float.class, float.class );
        tmp.put( Double.class, double.class );
        tmp.put( Boolean.class, boolean.class );
        tmp.put( Character.class, char.class );

        WRAPPER_TO_PRIMITIVE_TYPES = Collections.unmodifiableMap( tmp );

        tmp = new HashMap<>();
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

        Map<String, LiteralAnalyzer> tmp2 = new HashMap<>();
        tmp2.put( boolean.class.getCanonicalName(), new BooleanAnalyzer() );
        tmp2.put( Boolean.class.getCanonicalName(), new BooleanAnalyzer() );
        tmp2.put( char.class.getCanonicalName(), new CharAnalyzer() );
        tmp2.put( Character.class.getCanonicalName(), new CharAnalyzer() );
        tmp2.put( byte.class.getCanonicalName(), new ByteAnalyzer() );
        tmp2.put( Byte.class.getCanonicalName(), new ByteAnalyzer() );
        tmp2.put( double.class.getCanonicalName(), new DoubleAnalyzer() );
        tmp2.put( Double.class.getCanonicalName(), new DoubleAnalyzer() );
        tmp2.put( float.class.getCanonicalName(), new FloatAnalyzer() );
        tmp2.put( Float.class.getCanonicalName(), new FloatAnalyzer() );
        tmp2.put( int.class.getCanonicalName(), new IntAnalyzer() );
        tmp2.put( Integer.class.getCanonicalName(), new IntAnalyzer() );
        tmp2.put( long.class.getCanonicalName(), new LongAnalyzer() );
        tmp2.put( Long.class.getCanonicalName(), new LongAnalyzer() );
        tmp2.put( short.class.getCanonicalName(), new ShortAnalyzer() );
        tmp2.put( Short.class.getCanonicalName(), new ShortAnalyzer() );

        ANALYZERS = Collections.unmodifiableMap( tmp2 );

        TYPE_KIND_NAME.put( TypeKind.BOOLEAN, "boolean" );
        TYPE_KIND_NAME.put( TypeKind.BYTE, "byte" );
        TYPE_KIND_NAME.put( TypeKind.SHORT, "short" );
        TYPE_KIND_NAME.put( TypeKind.INT, "int" );
        TYPE_KIND_NAME.put( TypeKind.LONG, "long" );
        TYPE_KIND_NAME.put( TypeKind.CHAR, "char" );
        TYPE_KIND_NAME.put( TypeKind.FLOAT, "float" );
        TYPE_KIND_NAME.put( TypeKind.DOUBLE, "double" );

        Map<String, Integer> tmp3 = new HashMap<>(  );
        tmp3.put( byte.class.getName(), 1 );
        tmp3.put( Byte.class.getName(), 1 );
        tmp3.put( short.class.getName(), 2 );
        tmp3.put( Short.class.getName(), 2 );
        tmp3.put( int.class.getName(), 3 );
        tmp3.put( Integer.class.getName(), 3 );
        tmp3.put( long.class.getName(), 4 );
        tmp3.put( Long.class.getName(), 4 );
        tmp3.put( float.class.getName(), 5 );
        tmp3.put( Float.class.getName(), 5 );
        tmp3.put( double.class.getName(), 6 );
        tmp3.put( Double.class.getName(), 6 );
        tmp3.put( BigInteger.class.getName(), 50 );
        tmp3.put( BigDecimal.class.getName(), 51 );
        tmp3.put( String.class.getName(), 51 );
        NARROWING_LUT = Collections.unmodifiableMap( tmp3 );
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

    public static boolean isNative(String fullyQualifiedName) {
        return ANALYZERS.containsKey( fullyQualifiedName );
    }

    public static boolean isNumber(Class<?> clazz) {
        if ( clazz == null ) {
            return false;
        }
        else {
            return NUMBER_TYPES.contains( clazz );
        }
    }

    /**
     *
     * @param className FQN of the literal native class
     * @param literal literal to verify
     * @return literal class when the literal is a proper literal for the provided kind.
     * @throws IllegalArgumentException when the literal does not match to the provided native type className
     */
    public static Class<?> getLiteral(String className, String literal) {
        LiteralAnalyzer analyzer = ANALYZERS.get( className );
        Class result = null;
        if ( analyzer != null ) {
            analyzer.validate( literal );
            result = analyzer.getLiteral();
        }
        return result;
    }

    /**
     * The name that should be used for the {@code typeKind}.
     * Should be used in order to get the name of a primitive type
     *
     * @param typeKind the type kind
     *
     * @return the name that should be used for the {@code typeKind}
     */
    public static String getName(TypeKind typeKind) {
        return TYPE_KIND_NAME.get( typeKind );
    }

    public static boolean isNarrowing( String sourceFQN, String targetFQN ) {

        boolean isNarrowing = false;

        Integer sourcePosition = NARROWING_LUT.get( sourceFQN );
        Integer targetPosition = NARROWING_LUT.get( targetFQN );

        if ( sourcePosition != null && targetPosition != null ) {
            isNarrowing = ( targetPosition - sourcePosition < 0 );
        }
        return isNarrowing;
    }
}
