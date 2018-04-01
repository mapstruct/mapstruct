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
package org.mapstruct.ap.test.source.constants;

import javax.lang.model.type.TypeKind;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.ap.internal.util.NativeTypes.isStringAssignable;

/**
 *
 * @author Sjaak Derksen
 */
public class ConstantOptimizingTest {

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testUnderscorePlacement1() {
        assertThat( isStringAssignable( TypeKind.LONG, true, "1234_5678_9012_3456L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "999_99_9999L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "3.14_15F" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0xFF_EC_DE_5EL" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0xCAFE_BABEL" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0x7fff_ffff_ffff_ffffL" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.BYTE, true, "0b0010_0101" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0b11010010_01101001_10010100_10010010L" ) ).isTrue();
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
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "3_.1415F" ) ).isFalse();

        // Invalid: cannot put underscores
        // adjacent to a decimal point
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "3._1415F" ) ).isFalse();

        // Invalid: cannot put underscores
        // prior to an L suffix
        assertThat( isStringAssignable( TypeKind.LONG, true, "999_99_9999_L" ) ).isFalse();

        // OK (decimal literal)
        assertThat( isStringAssignable( TypeKind.INT, true, "5_2" ) ).isTrue();

        // Invalid: cannot put underscores
        // At the end of a literal
        assertThat( isStringAssignable( TypeKind.INT, true, "52_" ) ).isFalse();

        // OK (decimal literal)
        assertThat( isStringAssignable( TypeKind.INT, true, "5_______2" ) ).isTrue();

        // Invalid: cannot put underscores
        // in the 0x radix prefix
        assertThat( isStringAssignable( TypeKind.INT, true, "0_x52" ) ).isFalse();

        // Invalid: cannot put underscores
        // at the beginning of a number
        assertThat( isStringAssignable( TypeKind.INT, true, "0x_52" ) ).isFalse();

        // OK (hexadecimal literal)
        assertThat( isStringAssignable( TypeKind.INT, true, "0x5_2" ) ).isTrue();

        // Invalid: cannot put underscores
        // at the end of a number
        assertThat( isStringAssignable( TypeKind.INT, true, "0x52_" ) ).isFalse();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testIntegerLiteralFromJLS() {

        // largest positive int: dec / octal / int / binary
        assertThat( isStringAssignable( TypeKind.INT, true, "2147483647" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0x7fff_ffff" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0177_7777_7777" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0b0111_1111_1111_1111_1111_1111_1111_1111" ) ).isTrue();

        // most negative int: dec / octal / int / binary
        // NOTE parseInt should be changed to parseUnsignedInt in Java, than the - sign can disssapear (java8)
        // and the function will be true to what the compiler shows.
        assertThat( isStringAssignable( TypeKind.INT, true, "-2147483648" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0x8000_0000" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0200_0000_0000" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0b1000_0000_0000_0000_0000_0000_0000_0000" ) ).isTrue();

        // -1 representation int: dec / octal / int / binary
        assertThat( isStringAssignable( TypeKind.INT, true, "-1" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0xffff_ffff" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0377_7777_7777" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0b1111_1111_1111_1111_1111_1111_1111_1111" ) ).isTrue();

        // largest positive long: dec / octal / int / binary
        assertThat( isStringAssignable( TypeKind.LONG, true, "9223372036854775807L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0x7fff_ffff_ffff_ffffL" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "07_7777_7777_7777_7777_7777L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_"
            + "1111_1111_1111_1111_1111_1111L" ) ).isTrue();
        // most negative long: dec / octal / int / binary
        assertThat( isStringAssignable( TypeKind.LONG, true, "-9223372036854775808L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0x8000_0000_0000_0000L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "010_0000_0000_0000_0000_0000L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_"
            + "0000_0000_0000_0000_0000L" ) ).isTrue();
        // -1 representation long: dec / octal / int / binary
        assertThat( isStringAssignable( TypeKind.LONG, true, "-1L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0xffff_ffff_ffff_ffffL" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "017_7777_7777_7777_7777_7777L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_"
            + "1111_1111_1111_1111_1111L" ) ).isTrue();

        // some examples of ints
        assertThat( isStringAssignable( TypeKind.INT, true, "0" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "2" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0372" ) ).isTrue();
        //assertThat( isStringAssignable( TypeKind.INT, true, "0xDada_Cafe" ) ).isTrue(); java8
        assertThat( isStringAssignable( TypeKind.INT, true, "1996" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "0x00_FF__00_FF" ) ).isTrue();

        // some examples of longs
        assertThat( isStringAssignable( TypeKind.LONG, true, "0777l" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0x100000000L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "2_147_483_648L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0xC0B0L" ) ).isTrue();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testFloatingPoingLiteralFromJLS() {

        // The largest positive finite literal of type float is 3.4028235e38f.
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "3.4028235e38f" ) ).isTrue();
        // The smallest positive finite non-zero literal of type float is 1.40e-45f.
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "1.40e-45f" ) ).isTrue();
        // The largest positive finite literal of type double is 1.7976931348623157e308.
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "1.7976931348623157e308" ) ).isTrue();
        // The smallest positive finite non-zero literal of type double is 4.9e-324
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9e-324" ) ).isTrue();

        // some floats
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "3.1e1F" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "2.f" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, ".3f" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "0f" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "3.14f" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "6.022137e+23f" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "-3.14f" ) ).isTrue();

        // some doubles
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "1e1" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "1e+1" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "2." ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, ".3" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "0.0" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "3.14" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "-3.14" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "1e-9D" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "1e137" ) ).isTrue();

        // too large (infinitve)
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "3.4028235e38f" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "1.7976931348623157e308" ) ).isTrue();

        // too large (infinitve)
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "3.4028235e39f" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "1.7976931348623159e308" ) ).isFalse();

        // small
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "1.40e-45f" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "0x1.0p-149" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9e-324" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "0x0.001P-1062d" ) ).isTrue();

        // too small
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "1.40e-46f" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "0x1.0p-150" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9e-325" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "0x0.001p-1063d" ) ).isFalse();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testBooleanLiteralFromJLS() {
        assertThat( isStringAssignable( TypeKind.BOOLEAN, true, "true" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.BOOLEAN, true, "false" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.BOOLEAN, true, "FALSE" ) ).isFalse();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testCharLiteralFromJLS() {

        assertThat( isStringAssignable( TypeKind.CHAR, true, "'a'" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'%'" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'\t'" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'\\'" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'\''" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'\u03a9'" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'\uFFFF'" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'\177'" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'Ω'" ) ).isTrue();

    }

    @Test
    public void testShortAndByte() {
        assertThat( isStringAssignable( TypeKind.SHORT, true, "0xFE" ) ).isTrue();

        // some examples of ints
        assertThat( isStringAssignable( TypeKind.BYTE, true, "0" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.BYTE, true, "2" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.BYTE, true, "127" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.BYTE, true, "-128" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.SHORT, true, "1996" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.SHORT, true, "-1996" ) ).isTrue();
    }

    @Test
    public void testMiscellaneousErroneousPatterns() {
        assertThat( isStringAssignable( TypeKind.INT, true, "1F" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "1D" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.INT, true, "_1" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.INT, true, "1_" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.INT, true, "0x_1" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.INT, true, "0_x1" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9e_-3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9_e-3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4._9e-3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4_.9e-3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "_4.9e-3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9E-3_" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9E_-3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9E-_3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9E+-3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9E+_3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "4.9_E-3" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "0x0.001_P-10d" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "0x0.001P_-10d" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "0x0.001_p-10d" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.DOUBLE, true, "0x0.001p_-10d" ) ).isFalse();
    }

    @Test
    public void testNegatives() {
        assertThat( isStringAssignable( TypeKind.INT, true, "-0xffaa" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "-0377_7777" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.INT, true, "-0b1111_1111" ) ).isTrue();
    }

    @Test
    public void testFaultyChar() {
        assertThat( isStringAssignable( TypeKind.CHAR, true, "''" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'a" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'aa" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "a'" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "aa'" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "'" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.CHAR, true, "a" ) ).isFalse();
    }

    @Test
    public void testFloatWithLongLiteral() {
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "156L" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.FLOAT, true, "156l" ) ).isTrue();
    }

    @Test
    public void testLongPrimitivesAndNonRequiredLongSuffix() {
        assertThat( isStringAssignable( TypeKind.LONG, true, "156" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "156l" ) ).isTrue();
        assertThat( isStringAssignable( TypeKind.LONG, true, "156L" ) ).isTrue();
    }

    @Test
    public void testIntPrimitiveWithLongSuffix() {
        assertThat( isStringAssignable( TypeKind.INT, true, "156l" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.INT, true, "156L" ) ).isFalse();
    }

    @Test
    public void testTooBigIntegersAndBigLongs() {
        assertThat( isStringAssignable( TypeKind.INT, true, "0xFFFF_FFFF_FFFF" ) ).isFalse();
        assertThat( isStringAssignable( TypeKind.LONG, true, "0xFFFF_FFFF_FFFF_FFFF_FFFF" ) ).isFalse();
    }

    @Test
    public void testNonSupportedPrimitiveType() {
        assertThat( isStringAssignable( TypeKind.VOID, true, "0xFFFF_FFFF_FFFF" ) ).isFalse();
    }

}
