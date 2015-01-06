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
package org.mapstruct.ap.test.conversion.bignumbers;

import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Tests conversions between {@link BigInteger} and numbers as well as String.
 *
 * @author Gunnar Morling
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class BigNumbersConversionTest {

    @Test
    @IssueKey("21")
    @WithClasses({ BigIntegerSource.class, BigIntegerTarget.class, BigIntegerMapper.class })
    public void shouldApplyBigIntegerConversions() {
        BigIntegerSource source = new BigIntegerSource();
        source.setB( new BigInteger( "1" ) );
        source.setBb( new BigInteger( "2" ) );
        source.setS( new BigInteger( "3" ) );
        source.setSs( new BigInteger( "4" ) );
        source.setI( new BigInteger( "5" ) );
        source.setIi( new BigInteger( "6" ) );
        source.setL( new BigInteger( "7" ) );
        source.setLl( new BigInteger( "8" ) );
        source.setF( new BigInteger( "9" ) );
        source.setFf( new BigInteger( "10" ) );
        source.setD( new BigInteger( "11" ) );
        source.setDd( new BigInteger( "12" ) );
        source.setString( new BigInteger( "13" ) );

        BigIntegerTarget target = BigIntegerMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( (byte) 2 );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( (short) 4 );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( 6 );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( 8 );
        assertThat( target.getF() ).isEqualTo( 9.0f );
        assertThat( target.getFf() ).isEqualTo( 10.0f );
        assertThat( target.getD() ).isEqualTo( 11.0d );
        assertThat( target.getDd() ).isEqualTo( 12.0d );
        assertThat( target.getString() ).isEqualTo( "13" );
    }

    @Test
    @IssueKey("21")
    @WithClasses({ BigIntegerSource.class, BigIntegerTarget.class, BigIntegerMapper.class })
    public void shouldApplyReverseBigIntegerConversions() {
        BigIntegerTarget target = new BigIntegerTarget();
        target.setB( (byte) 1 );
        target.setBb( (byte) 2 );
        target.setS( (short) 3 );
        target.setSs( (short) 4 );
        target.setI( 5 );
        target.setIi( 6 );
        target.setL( 7 );
        target.setLl( 8L );
        target.setF( 9.0f );
        target.setFf( 10.0f );
        target.setD( 11.0d );
        target.setDd( 12.0d );
        target.setString( "13" );

        BigIntegerSource source = BigIntegerMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getB() ).isEqualTo( new BigInteger( "1" ) );
        assertThat( source.getBb() ).isEqualTo( new BigInteger( "2" ) );
        assertThat( source.getS() ).isEqualTo( new BigInteger( "3" ) );
        assertThat( source.getSs() ).isEqualTo( new BigInteger( "4" ) );
        assertThat( source.getI() ).isEqualTo( new BigInteger( "5" ) );
        assertThat( source.getIi() ).isEqualTo( new BigInteger( "6" ) );
        assertThat( source.getL() ).isEqualTo( new BigInteger( "7" ) );
        assertThat( source.getLl() ).isEqualTo( new BigInteger( "8" ) );
        assertThat( source.getF() ).isEqualTo( new BigInteger( "9" ) );
        assertThat( source.getFf() ).isEqualTo( new BigInteger( "10" ) );
        assertThat( source.getD() ).isEqualTo( new BigInteger( "11" ) );
        assertThat( source.getDd() ).isEqualTo( new BigInteger( "12" ) );
        assertThat( source.getString() ).isEqualTo( new BigInteger( "13" ) );
    }

    @Test
    @IssueKey("21")
    @WithClasses({ BigDecimalSource.class, BigDecimalTarget.class, BigDecimalMapper.class })
    public void shouldApplyBigDecimalConversions() {
        BigDecimalSource source = new BigDecimalSource();
        source.setB( new BigDecimal( "1.45" ) );
        source.setBb( new BigDecimal( "2.45" ) );
        source.setS( new BigDecimal( "3.45" ) );
        source.setSs( new BigDecimal( "4.45" ) );
        source.setI( new BigDecimal( "5.45" ) );
        source.setIi( new BigDecimal( "6.45" ) );
        source.setL( new BigDecimal( "7.45" ) );
        source.setLl( new BigDecimal( "8.45" ) );
        source.setF( new BigDecimal( "9.45" ) );
        source.setFf( new BigDecimal( "10.45" ) );
        source.setD( new BigDecimal( "11.45" ) );
        source.setDd( new BigDecimal( "12.45" ) );
        source.setString( new BigDecimal( "13.45" ) );
        source.setBigInteger( new BigDecimal( "14.45" ) );

        BigDecimalTarget target = BigDecimalMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( (byte) 2 );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( (short) 4 );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( 6 );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( 8 );
        assertThat( target.getF() ).isEqualTo( 9.45f );
        assertThat( target.getFf() ).isEqualTo( 10.45f );
        assertThat( target.getD() ).isEqualTo( 11.45d );
        assertThat( target.getDd() ).isEqualTo( 12.45d );
        assertThat( target.getString() ).isEqualTo( "13.45" );
        assertThat( target.getBigInteger() ).isEqualTo( new BigInteger( "14" ) );
    }

    @Test
    @IssueKey("21")
    @WithClasses({ BigDecimalSource.class, BigDecimalTarget.class, BigDecimalMapper.class })
    public void shouldApplyReverseBigDecimalConversions() {
        BigDecimalTarget target = new BigDecimalTarget();
        target.setB( (byte) 1 );
        target.setBb( (byte) 2 );
        target.setS( (short) 3 );
        target.setSs( (short) 4 );
        target.setI( 5 );
        target.setIi( 6 );
        target.setL( 7 );
        target.setLl( 8L );
        target.setF( 9.0f );
        target.setFf( 10.0f );
        target.setD( 11.0d );
        target.setDd( 12.0d );
        target.setString( "13.45" );
        target.setBigInteger( new BigInteger( "14" ) );

        BigDecimalSource source = BigDecimalMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getB() ).isEqualTo( new BigDecimal( "1" ) );
        assertThat( source.getBb() ).isEqualTo( new BigDecimal( "2" ) );
        assertThat( source.getS() ).isEqualTo( new BigDecimal( "3" ) );
        assertThat( source.getSs() ).isEqualTo( new BigDecimal( "4" ) );
        assertThat( source.getI() ).isEqualTo( new BigDecimal( "5" ) );
        assertThat( source.getIi() ).isEqualTo( new BigDecimal( "6" ) );
        assertThat( source.getL() ).isEqualTo( new BigDecimal( "7" ) );
        assertThat( source.getLl() ).isEqualTo( new BigDecimal( "8" ) );
        assertThat( source.getF() ).isEqualTo( new BigDecimal( "9.0" ) );
        assertThat( source.getFf() ).isEqualTo( new BigDecimal( "10.0" ) );
        assertThat( source.getD() ).isEqualTo( new BigDecimal( "11.0" ) );
        assertThat( source.getDd() ).isEqualTo( new BigDecimal( "12.0" ) );
        assertThat( source.getString() ).isEqualTo( new BigDecimal( "13.45" ) );
        assertThat( source.getBigInteger() ).isEqualTo( new BigDecimal( "14" ) );
    }
}
