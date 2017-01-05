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
package org.mapstruct.ap.test.conversion.numbers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@WithClasses({
        Source.class,
        Target.class,
        SourceTargetMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class NumberFormatConversionTest {

    @Before
    public void setDefaultLocale() {
        Locale.setDefault( Locale.ENGLISH );
    }

    @Test
    public void shouldApplyStringConversions() {
        Source source = new Source();
        source.setI( 1 );
        source.setIi( 2 );
        source.setD( 3.0 );
        source.setDd( 4.0 );
        source.setF( 3.0f );
        source.setFf( 4.0f );
        source.setL( 5L );
        source.setLl( 6L );
        source.setB( (byte) 7 );
        source.setBb( (byte) 8 );

        source.setComplex1( 345346.456756 );
        source.setComplex2( 5007034.3 );

        source.setBigDecimal1( new BigDecimal( "987E-20" ) );
        source.setBigInteger1( new BigInteger( "1234567890000" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getI() ).isEqualTo( "1.00" );
        assertThat( target.getIi() ).isEqualTo( "2.00" );
        assertThat( target.getD() ).isEqualTo( "3.00" );
        assertThat( target.getDd() ).isEqualTo( "4.00" );
        assertThat( target.getF() ).isEqualTo( "3.00" );
        assertThat( target.getFf() ).isEqualTo( "4.00" );
        assertThat( target.getL() ).isEqualTo( "5.00" );
        assertThat( target.getLl() ).isEqualTo( "6.00" );
        assertThat( target.getB() ).isEqualTo( "7.00" );
        assertThat( target.getBb() ).isEqualTo( "8.00" );

        assertThat( target.getComplex1() ).isEqualTo( "345.35E3" );
        assertThat( target.getComplex2() ).isEqualTo( "$5007034.30" );

        assertThat( target.getBigDecimal1() ).isEqualTo( "9.87E-18" );
        assertThat( target.getBigInteger1() ).isEqualTo( "1.23456789E12" );
    }

    @Test
    public void shouldApplyReverseStringConversions() {
        Target target = new Target();
        target.setI( "1.00" );
        target.setIi( "2.00" );
        target.setD( "3.00" );
        target.setDd( "4.00" );
        target.setF( "3.00" );
        target.setFf( "4.00" );
        target.setL( "5.00" );
        target.setLl( "6.00" );
        target.setB( "7.00" );
        target.setBb( "8.00" );

        target.setComplex1( "345.35E3" );
        target.setComplex2( "$5007034.30" );

        target.setBigDecimal1( "9.87E-18" );
        target.setBigInteger1( "1.23456789E12" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getI() ).isEqualTo( 1 );
        assertThat( source.getIi() ).isEqualTo( Integer.valueOf( 2 ) );
        assertThat( source.getD() ).isEqualTo( 3.0 );
        assertThat( source.getDd() ).isEqualTo( Double.valueOf( 4.0 ) );
        assertThat( source.getF() ).isEqualTo( 3.0f );
        assertThat( source.getFf() ).isEqualTo( Float.valueOf( 4.0f ) );
        assertThat( source.getL() ).isEqualTo( 5L );
        assertThat( source.getLl() ).isEqualTo( Long.valueOf( 6L ) );
        assertThat( source.getB() ).isEqualTo( (byte) 7 );
        assertThat( source.getBb() ).isEqualTo( (byte) 8 );

        assertThat( source.getComplex1() ).isEqualTo( 345350.0 );
        assertThat( source.getComplex2() ).isEqualTo( 5007034.3 );

        assertThat( source.getBigDecimal1() ).isEqualTo( new BigDecimal( "987E-20" ) );
        assertThat( source.getBigInteger1() ).isEqualTo( new BigInteger( "1234567890000" ) );
    }

    @Test
    public void shouldApplyStringConversionsToIterables() {

        List<String> target = SourceTargetMapper.INSTANCE.sourceToTarget( Arrays.asList( 2f ) );

        assertThat( target ).hasSize( 1 );
        assertThat( target ).isEqualTo( Arrays.asList( "2.00" ) );

        List<Float> source = SourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source  ).hasSize( 1 );
        assertThat( source ).isEqualTo( Arrays.asList( 2.00f ) );
    }

    @Test
    public void shouldApplyStringConversionsToMaps() {

        Map<Float, Float> source1 = new HashMap<Float, Float>();
        source1.put( 1.0001f, 2.01f );

        Map<String, String> target = SourceTargetMapper.INSTANCE.sourceToTarget( source1 );
        assertThat( target  ).hasSize( 1 );
        assertThat( target ).contains( entry( "1.00", "2" ) );

        Map<Float, Float> source2 = SourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source2  ).hasSize( 1 );
        assertThat( source2 ).contains( entry( 1.00f, 2f ) );

    }
}
