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

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mapstruct.ap.internal.util.NativeTypes.getLiteral;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;

import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;
import javax.lang.model.type.TypeVisitor;

/**
 * @author Ciaran Liedeman
 */
public class NativeTypesTest {

    @Test
    public void testIsNumber() throws Exception {
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
        assertThat( getLiteral( TypeKind.LONG, "1234_5678_9012_3456L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "999_99_9999L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, "3.14_15F", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0xFF_EC_DE_5EL", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0xCAFE_BABEL", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0x7fff_ffff_ffff_ffffL", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.BYTE, "0b0010_0101", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0b11010010_01101001_10010100_10010010L", types() ) ).isNotNull();
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
        assertThat( getLiteral( TypeKind.FLOAT, "3_.1415F", types() ) ).isNull();

        // Invalid: cannot put underscores
        // adjacent to a decimal point
        assertThat( getLiteral( TypeKind.FLOAT, "3._1415F", types() ) ).isNull();

        // Invalid: cannot put underscores
        // prior to an L suffix
        assertThat( getLiteral( TypeKind.LONG, "999_99_9999_L", types() ) ).isNull();

        // OK (decimal literal)
        assertThat( getLiteral( TypeKind.INT, "5_2", types() ) ).isNotNull();

        // Invalid: cannot put underscores
        // At the end of a literal
        assertThat( getLiteral( TypeKind.INT, "52_", types() ) ).isNull();

        // OK (decimal literal)
        assertThat( getLiteral( TypeKind.INT, "5_______2", types() ) ).isNotNull();

        // Invalid: cannot put underscores
        // in the 0x radix prefix
        assertThat( getLiteral( TypeKind.INT, "0_x52", types() ) ).isNull();

        // Invalid: cannot put underscores
        // at the beginning of a number
        assertThat( getLiteral( TypeKind.INT, "0x_52", types() ) ).isNull();

        // OK (hexadecimal literal)
        assertThat( getLiteral( TypeKind.INT, "0x5_2", types() ) ).isNotNull();

        // Invalid: cannot put underscores
        // at the end of a number
        assertThat( getLiteral( TypeKind.INT, "0x52_", types() ) ).isNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testIntegerLiteralFromJLS() {

        // largest positive int: dec / octal / int / binary
        assertThat( getLiteral( TypeKind.INT, "2147483647", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "2147483647", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0x7fff_ffff", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0177_7777_7777", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0b0111_1111_1111_1111_1111_1111_1111_1111", types() ) ).isNotNull();

        // most negative int: dec / octal / int / binary
        // NOTE parseInt should be changed to parseUnsignedInt in Java, than the - sign can disssapear (java8)
        // and the function will be true to what the compiler shows.
        assertThat( getLiteral( TypeKind.INT, "-2147483648", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0x8000_0000", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0200_0000_0000", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0b1000_0000_0000_0000_0000_0000_0000_0000", types() ) ).isNotNull();

        // -1 representation int: dec / octal / int / binary
        assertThat( getLiteral( TypeKind.INT, "-1", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0xffff_ffff", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0377_7777_7777", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0b1111_1111_1111_1111_1111_1111_1111_1111", types() ) ).isNotNull();

        // largest positive long: dec / octal / int / binary
        assertThat( getLiteral( TypeKind.LONG, "9223372036854775807L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0x7fff_ffff_ffff_ffffL", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "07_7777_7777_7777_7777_7777L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_"
            + "1111_1111_1111_1111_1111_1111L", types() ) ).isNotNull();
        // most negative long: dec / octal / int / binary
        assertThat( getLiteral( TypeKind.LONG, "-9223372036854775808L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0x8000_0000_0000_0000L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "010_0000_0000_0000_0000_0000L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_"
            + "0000_0000_0000_0000_0000L", types() ) ).isNotNull();
        // -1 representation long: dec / octal / int / binary
        assertThat( getLiteral( TypeKind.LONG, "-1L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0xffff_ffff_ffff_ffffL", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "017_7777_7777_7777_7777_7777L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_"
            + "1111_1111_1111_1111_1111L", types() ) ).isNotNull();

        // some examples of ints
        assertThat( getLiteral( TypeKind.INT, "0", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "2", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0372", types() ) ).isNotNull();
        //assertThat( getLiteral( TypeKind.INT, "0xDada_Cafe", types() ) ).isNotNull(); java8
        assertThat( getLiteral( TypeKind.INT, "1996", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "0x00_FF__00_FF", types() ) ).isNotNull();

        // some examples of longs
        assertThat( getLiteral( TypeKind.LONG, "0777l", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0x100000000L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "2_147_483_648L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "0xC0B0L", types() ) ).isNotNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testFloatingPoingLiteralFromJLS() {

        // The largest positive finite literal of type float is 3.4028235e38f.
        assertThat( getLiteral( TypeKind.FLOAT, "3.4028235e38f", types() ) ).isNotNull();
        // The smallest positive finite non-zero literal of type float is 1.40e-45f.
        assertThat( getLiteral( TypeKind.FLOAT, "1.40e-45f", types() ) ).isNotNull();
        // The largest positive finite literal of type double is 1.7976931348623157e308.
        assertThat( getLiteral( TypeKind.DOUBLE, "1.7976931348623157e308", types() ) ).isNotNull();
        // The smallest positive finite non-zero literal of type double is 4.9e-324
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9e-324", types() ) ).isNotNull();

        // some floats
        assertThat( getLiteral( TypeKind.FLOAT, "3.1e1F", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, "2.f", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, ".3f", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, "0f", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, "3.14f", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, "6.022137e+23f", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, "-3.14f", types() ) ).isNotNull();

        // some doubles
        assertThat( getLiteral( TypeKind.DOUBLE, "1e1", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "1e+1", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "2.", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, ".3", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "0.0", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "3.14", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "-3.14", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "1e-9D", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "1e137", types() ) ).isNotNull();

        // too large (infinitve)
        assertThat( getLiteral( TypeKind.FLOAT, "3.4028235e38f", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "1.7976931348623157e308", types() ) ).isNotNull();

        // too large (infinitve)
        assertThat( getLiteral( TypeKind.FLOAT, "3.4028235e39f", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "1.7976931348623159e308", types() ) ).isNull();

        // small
        assertThat( getLiteral( TypeKind.FLOAT, "1.40e-45f", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, "0x1.0p-149", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9e-324", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "0x0.001P-1062d", types() ) ).isNotNull();

        // too small
        assertThat( getLiteral( TypeKind.FLOAT, "1.40e-46f", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.FLOAT, "0x1.0p-150", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9e-325", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "0x0.001p-1063d", types() ) ).isNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testBooleanLiteralFromJLS() {
        assertThat( getLiteral( TypeKind.BOOLEAN, "true", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.BOOLEAN, "false", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.BOOLEAN, "FALSE", types() ) ).isNull();
    }

    /**
     * checkout https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
     *
     * The following example shows other ways you can use the underscore in numeric literals:
     */
    @Test
    public void testCharLiteralFromJLS() {

        assertThat( getLiteral( TypeKind.CHAR, "'a'", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.CHAR, "'%'", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.CHAR, "'\t'", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.CHAR, "'\\'", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.CHAR, "'\''", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.CHAR, "'\u03a9'", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.CHAR, "'\uFFFF'", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.CHAR, "'\177'", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.CHAR, "'Ω'", types() ) ).isNotNull();

    }

    @Test
    public void testShortAndByte() {
        assertThat( getLiteral( TypeKind.SHORT, "0xFE", types() ) ).isNotNull();

        // some examples of ints
        assertThat( getLiteral( TypeKind.BYTE, "0", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.BYTE, "2", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.BYTE, "127", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.BYTE, "-128", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.SHORT, "1996", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.SHORT, "-1996", types() ) ).isNotNull();
    }

    @Test
    public void testMiscellaneousErroneousPatterns() {
        assertThat( getLiteral( TypeKind.INT, "1F", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.FLOAT, "1D", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.INT, "_1", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.INT, "1_", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.INT, "0x_1", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.INT, "0_x1", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9e_-3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9_e-3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4._9e-3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4_.9e-3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "_4.9e-3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9E-3_", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9E_-3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9E-_3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9E+-3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9E+_3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "4.9_E-3", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "0x0.001_P-10d", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "0x0.001P_-10d", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "0x0.001_p-10d", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.DOUBLE, "0x0.001p_-10d", types() ) ).isNull();
    }

    @Test
    public void testNegatives() {
        assertThat( getLiteral( TypeKind.INT, "-0xffaa", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "-0377_7777", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.INT, "-0b1111_1111", types() ) ).isNotNull();
    }

    @Test
    public void testFaultyChar() {
        assertThat( getLiteral( TypeKind.CHAR, "''", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.CHAR, "'a", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.CHAR, "'aa", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.CHAR, "a'", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.CHAR, "aa'", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.CHAR, "'", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.CHAR, "a", types() ) ).isNull();
    }

    @Test
    public void testFloatWithLongLiteral() {
        assertThat( getLiteral( TypeKind.FLOAT, "156L", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.FLOAT, "156l", types() ) ).isNotNull();
    }

    @Test
    public void testLongPrimitivesWithLongSuffix() {
        assertThat( getLiteral( TypeKind.LONG, "156l", types() ) ).isNotNull();
        assertThat( getLiteral( TypeKind.LONG, "156L", types() ) ).isNotNull();
    }

    @Test
    public void testIntPrimitiveWithLongSuffix() {
        assertThat( getLiteral( TypeKind.INT, "156l", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.INT, "156L", types() ) ).isNull();
    }

    @Test
    public void testTooBigIntegersAndBigLongs() {
        assertThat( getLiteral( TypeKind.INT, "0xFFFF_FFFF_FFFF", types() ) ).isNull();
        assertThat( getLiteral( TypeKind.LONG, "0xFFFF_FFFF_FFFF_FFFF_FFFF", types() ) ).isNull();
    }

    @Test
    public void testNonSupportedPrimitiveType() {
        assertThat( getLiteral( TypeKind.VOID, "0xFFFF_FFFF_FFFF", types() ) ).isNull();
    }

    private static Types types() {

        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ( "getPrimitiveType".equals( method.getName() ) ) {
                    return new MyTypeMirror();
                }
                else {
                    return null;
                }
            }
        };

        return (Types) Proxy.newProxyInstance( Types.class.getClassLoader(),  new Class[]{Types.class}, handler );
    }

    private static class MyTypeMirror implements PrimitiveType {

        @Override
        public TypeKind getKind() {
            return TypeKind.VOID;
        }

        @Override
        public <R, P> R accept(TypeVisitor<R, P> v, P p) {
            return null;
        }

        @Override
        public List<? extends AnnotationMirror> getAnnotationMirrors() {
            return null;
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
            return null;
        }

        @Override
        public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
            return null;
        }

    }
}
