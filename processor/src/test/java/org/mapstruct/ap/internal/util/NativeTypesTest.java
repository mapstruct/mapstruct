/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

/**
 * @author Ciaran Liedeman
 */
public class NativeTypesTest {

    @Test
    public void testIsNumber() {
        assertFalse( NativeTypes.isNumber( null ) );
        assertFalse( NativeTypes.isNumber( Object.class ) );
        assertFalse( NativeTypes.isNumber( String.class ) );

        assertTrue( NativeTypes.isNumber( double.class ) );
        assertTrue( NativeTypes.isNumber( Double.class ) );
        assertTrue( NativeTypes.isNumber( long.class ) );
        assertTrue( NativeTypes.isNumber( Long.class ) );
        assertTrue( NativeTypes.isNumber( BigDecimal.class ) );
        assertTrue( NativeTypes.isNumber( BigInteger.class ) );
    }

   /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testUnderscorePlacement1() {
        assertThat( getLiteral( long.class.getCanonicalName(), "1234_5678_9012_3456L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "999_99_9999L" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "3.14_15F" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0xFF_EC_DE_5EL" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0xCAFE_BABEL" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0x7fff_ffff_ffff_ffffL" ) ).isNotNull();
        assertThat( getLiteral( byte.class.getCanonicalName(), "0b0010_0101" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0b11010010_01101001_10010100_10010010L" ) )
            .isNotNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * You can place underscores only between digits; you cannot place underscores in the following places:
     * <ol>
     * <li>At the beginning or end of a number</li>
     * <li>Adjacent to a decimal point in a floating point literal</li>
     * <li>Prior to an F or L suffix</li>
     * <li>In positions where a string of digits is expected</li>
     * </ol>
     * The following examples demonstrate valid and invalid underscore placements (which are highlighted) in numeric
     * literals:
     */
    @Test
    public void testUnderscorePlacement2() {

        // Invalid: cannot put underscores
        // adjacent to a decimal point
        assertThat( getLiteral( float.class.getCanonicalName(), "3_.1415F" ) ).isNull();

        // Invalid: cannot put underscores
        // adjacent to a decimal point
        assertThat( getLiteral( float.class.getCanonicalName(), "3._1415F" ) ).isNull();

        // Invalid: cannot put underscores
        // prior to an L suffix
        assertThat( getLiteral( long.class.getCanonicalName(), "999_99_9999_L" ) ).isNull();

        // OK (decimal literal)
        assertThat( getLiteral( int.class.getCanonicalName(), "5_2" ) ).isNotNull();

        // Invalid: cannot put underscores
        // At the end of a literal
        assertThat( getLiteral( int.class.getCanonicalName(), "52_" ) ).isNull();

        // OK (decimal literal)
        assertThat( getLiteral( int.class.getCanonicalName(), "5_______2" ) ).isNotNull();

        // Invalid: cannot put underscores
        // in the 0x radix prefix
        assertThat( getLiteral( int.class.getCanonicalName(), "0_x52" ) ).isNull();

        // Invalid: cannot put underscores
        // at the beginning of a number
        assertThat( getLiteral( int.class.getCanonicalName(), "0x_52" ) ).isNull();

        // OK (hexadecimal literal)
        assertThat( getLiteral( int.class.getCanonicalName(), "0x5_2" ) ).isNotNull();

        // Invalid: cannot put underscores
        // at the end of a number
        assertThat( getLiteral( int.class.getCanonicalName(), "0x52_" ) ).isNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testIntegerLiteralFromJLS() {

        // largest positive int: dec / octal / int / binary
        assertThat( getLiteral( int.class.getCanonicalName(), "2147483647" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "2147483647" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0x7fff_ffff" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0177_7777_7777" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0b0111_1111_1111_1111_1111_1111_1111_1111" ) )
            .isNotNull();

        // most negative int: dec / octal / int / binary
        // NOTE parseInt should be changed to parseUnsignedInt in Java, than the - sign can dissapear (java8)
        // and the function will be true to what the compiler shows.
        assertThat( getLiteral( int.class.getCanonicalName(), "-2147483648" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0x8000_0000" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0200_0000_0000" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0b1000_0000_0000_0000_0000_0000_0000_0000" ) )
            .isNotNull();

        // -1 representation int: dec / octal / int / binary
        assertThat( getLiteral( int.class.getCanonicalName(), "-1" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0xffff_ffff" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0377_7777_7777" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0b1111_1111_1111_1111_1111_1111_1111_1111" ) )
            .isNotNull();

        // largest positive long: dec / octal / int / binary
        assertThat( getLiteral( long.class.getCanonicalName(), "9223372036854775807L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0x7fff_ffff_ffff_ffffL" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "07_7777_7777_7777_7777_7777L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_"
            + "1111_1111_1111_1111_1111_1111L" ) ).isNotNull();
        // most negative long: dec / octal / int / binary
        assertThat( getLiteral( long.class.getCanonicalName(), "-9223372036854775808L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0x8000_0000_0000_0000L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "010_0000_0000_0000_0000_0000L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_"
            + "0000_0000_0000_0000_0000_0000L" ) ).isNotNull();
        // -1 representation long: dec / octal / int / binary
        assertThat( getLiteral( long.class.getCanonicalName(), "-1L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0xffff_ffff_ffff_ffffL" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "017_7777_7777_7777_7777_7777L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_"
            + "1111_1111_1111_1111_1111_1111L" ) ).isNotNull();

        // some examples of ints
        assertThat( getLiteral( int.class.getCanonicalName(), "0" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "2" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0372" ) ).isNotNull();
        //assertThat( getLiteral( int.class.getCanonicalName(), "0xDada_Cafe" ) ).isNotNull(); java8
        assertThat( getLiteral( int.class.getCanonicalName(), "1996" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0x00_FF__00_FF" ) ).isNotNull();

        // some examples of longs
        assertThat( getLiteral( long.class.getCanonicalName(), "0777l" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0x100000000L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "2_147_483_648L" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0xC0B0L" ) ).isNotNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testFloatingPointLiteralFromJLS() {

        // The largest positive finite literal of type float is 3.4028235e38f.
        assertThat( getLiteral( float.class.getCanonicalName(), "3.4028235e38f" ) ).isNotNull();
        // The smallest positive finite non-zero literal of type float is 1.40e-45f.
        assertThat( getLiteral( float.class.getCanonicalName(), "1.40e-45f" ) ).isNotNull();
        // The largest positive finite literal of type double is 1.7976931348623157e308.
        assertThat( getLiteral( double.class.getCanonicalName(), "1.7976931348623157e308" ) ).isNotNull();
        // The smallest positive finite non-zero literal of type double is 4.9e-324
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9e-324" ) ).isNotNull();

        // some floats
        assertThat( getLiteral( float.class.getCanonicalName(), "3.1e1F" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "2.f" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), ".3f" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "0f" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "3.14f" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "6.022137e+23f" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "-3.14f" ) ).isNotNull();

        // some doubles
        assertThat( getLiteral( double.class.getCanonicalName(), "1e1" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "1e+1" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "2." ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), ".3" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "0.0" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "3.14" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "-3.14" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "1e-9D" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "1e137" ) ).isNotNull();

        // too large (infinitve)
        assertThat( getLiteral( float.class.getCanonicalName(), "3.4028235e38f" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "1.7976931348623157e308" ) ).isNotNull();

        // too large (infinitve)
        assertThat( getLiteral( float.class.getCanonicalName(), "3.4028235e39f" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "1.7976931348623159e308" ) ).isNull();

        // small
        assertThat( getLiteral( float.class.getCanonicalName(), "1.40e-45f" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "0x1.0p-149" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9e-324" ) ).isNotNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "0x0.001P-1062d" ) ).isNotNull();

        // too small
        assertThat( getLiteral( float.class.getCanonicalName(), "1.40e-46f" ) ).isNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "0x1.0p-150" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9e-325" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "0x0.001p-1063d" ) ).isNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testBooleanLiteralFromJLS() {
        assertThat( getLiteral( boolean.class.getCanonicalName(), "true" ) ).isNotNull();
        assertThat( getLiteral( boolean.class.getCanonicalName(), "false" ) ).isNotNull();
        assertThat( getLiteral( boolean.class.getCanonicalName(), "FALSE" ) ).isNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testCharLiteralFromJLS() {

        assertThat( getLiteral( char.class.getCanonicalName(), "'a'" ) ).isNotNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'%'" ) ).isNotNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'\t'" ) ).isNotNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'\\'" ) ).isNotNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'\''" ) ).isNotNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'\u03a9'" ) ).isNotNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'\uFFFF'" ) ).isNotNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'\177'" ) ).isNotNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'Ω'" ) ).isNotNull();

    }

    @Test
    public void testShortAndByte() {
        assertThat( getLiteral( short.class.getCanonicalName(), "0xFE" ) ).isNotNull();

        // some examples of ints
        assertThat( getLiteral( byte.class.getCanonicalName(), "0" ) ).isNotNull();
        assertThat( getLiteral( byte.class.getCanonicalName(), "2" ) ).isNotNull();
        assertThat( getLiteral( byte.class.getCanonicalName(), "127" ) ).isNotNull();
        assertThat( getLiteral( byte.class.getCanonicalName(), "-128" ) ).isNotNull();
        assertThat( getLiteral( short.class.getCanonicalName(), "1996" ) ).isNotNull();
        assertThat( getLiteral( short.class.getCanonicalName(), "-1996" ) ).isNotNull();
    }

    @Test
    public void testMiscellaneousErroneousPatterns() {
        assertThat( getLiteral( int.class.getCanonicalName(), "1F" ) ).isNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "1D" ) ).isNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "_1" ) ).isNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "1_" ) ).isNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0x_1" ) ).isNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "0_x1" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9e_-3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9_e-3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4._9e-3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4_.9e-3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "_4.9e-3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9E-3_" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9E_-3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9E-_3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9E+-3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9E+_3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "4.9_E-3" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "0x0.001_P-10d" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "0x0.001P_-10d" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "0x0.001_p-10d" ) ).isNull();
        assertThat( getLiteral( double.class.getCanonicalName(), "0x0.001p_-10d" ) ).isNull();
    }

    @Test
    public void testNegatives() {
        assertThat( getLiteral( int.class.getCanonicalName(), "-0xffaa" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "-0377_7777" ) ).isNotNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "-0b1111_1111" ) ).isNotNull();
    }

    @Test
    public void testFaultyChar() {
        assertThat( getLiteral( char.class.getCanonicalName(), "''" ) ).isNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'a" ) ).isNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'aa" ) ).isNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "a'" ) ).isNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "aa'" ) ).isNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "'" ) ).isNull();
        assertThat( getLiteral( char.class.getCanonicalName(), "a" ) ).isNull();
    }

    @Test
    public void testFloatWithLongLiteral() {
        assertThat( getLiteral( float.class.getCanonicalName(), "156L" ) ).isNotNull();
        assertThat( getLiteral( float.class.getCanonicalName(), "156l" ) ).isNotNull();
    }

    @Test
    public void testLongPrimitivesWithLongSuffix() {
        assertThat( getLiteral( long.class.getCanonicalName(), "156l" ) ).isNotNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "156L" ) ).isNotNull();
    }

    @Test
    public void testIntPrimitiveWithLongSuffix() {
        assertThat( getLiteral( int.class.getCanonicalName(), "156l" ) ).isNull();
        assertThat( getLiteral( int.class.getCanonicalName(), "156L" ) ).isNull();
    }

    @Test
    public void testTooBigIntegersAndBigLongs() {
        assertThat( getLiteral( int.class.getCanonicalName(), "0xFFFF_FFFF_FFFF" ) ).isNull();
        assertThat( getLiteral( long.class.getCanonicalName(), "0xFFFF_FFFF_FFFF_FFFF_FFFF" ) ).isNull();
    }

    @Test
    public void testNonSupportedPrimitiveType() {
        assertThat( getLiteral( void.class.getCanonicalName(), "0xFFFF_FFFF_FFFF" ) ).isNull();
    }

    private static Class<?> getLiteral(String className, String literal) {
        try {
            return NativeTypes.getLiteral( className, literal );
        }
        catch ( IllegalArgumentException ex ) {
            return null;
        }
    }
}
