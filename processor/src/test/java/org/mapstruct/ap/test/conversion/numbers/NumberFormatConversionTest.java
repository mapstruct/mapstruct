/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junitpioneer.jupiter.DefaultLocale;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@WithClasses({
        Source.class,
        Target.class,
        SourceTargetMapper.class
})
@DefaultLocale("en")
public class NumberFormatConversionTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( SourceTargetMapper.class );

    @ProcessorTest
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

    @ProcessorTest
    public void shouldApplyStringConversionsWithCustomLocale() {
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

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetWithCustomLocale( source );

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

        assertThat( target.getBigDecimal1() ).isEqualTo( "9,87E-18" );
        assertThat( target.getBigInteger1() ).isEqualTo( "1,23456789E12" );
    }

    @ProcessorTest
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

    @ProcessorTest
    public void shouldApplyReverseStringConversionsWithCustomLocale() {
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

        target.setBigDecimal1( "9,87E-18" );
        target.setBigInteger1( "1,23456789E12" );

        Source source = SourceTargetMapper.INSTANCE.targetToSourceWithCustomLocale( target );

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

    @ProcessorTest
    public void shouldApplyStringConversionsToIterables() {

        List<String> target = SourceTargetMapper.INSTANCE.sourceToTarget( List.of( 2f ) );

        assertThat( target ).hasSize( 1 );
        assertThat( target ).isEqualTo( List.of( "2.00" ) );

        List<Float> source = SourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source  ).hasSize( 1 );
        assertThat( source ).isEqualTo( List.of( 2.00f ) );
    }

    @ProcessorTest
    public void shouldApplyStringConversionsToIterablesWithCustomLocale() {

        List<String> target = SourceTargetMapper.INSTANCE.sourceToTargetWithCustomLocale(
            List.of( new BigDecimal("987E-20") )
        );

        assertThat( target ).hasSize( 1 );
        assertThat( target ).isEqualTo( List.of( "9,87E-18" ) );

        List<BigDecimal> source = SourceTargetMapper.INSTANCE.targetToSourceWithCustomLocale( target );
        assertThat( source  ).hasSize( 1 );
        assertThat( source ).isEqualTo( List.of( new BigDecimal("987E-20") ) );
    }

    @ProcessorTest
    public void shouldApplyStringConversionsToMaps() {

        Map<Float, Float> source1 = new HashMap<>();
        source1.put( 1.0001f, 2.01f );

        Map<String, String> target = SourceTargetMapper.INSTANCE.sourceToTarget( source1 );
        assertThat( target  ).hasSize( 1 );
        assertThat( target ).contains( entry( "1.00", "2" ) );

        Map<Float, Float> source2 = SourceTargetMapper.INSTANCE.targetToSource( target );
        assertThat( source2  ).hasSize( 1 );
        assertThat( source2 ).contains( entry( 1.00f, 2f ) );

    }

    @ProcessorTest
    public void shouldApplyStringConversionsToMapsWithCustomLocale() {

        Map<BigDecimal, BigDecimal> source1 = new HashMap<>();
        source1.put( new BigDecimal( "987E-20" ), new BigDecimal( "97E-10" ) );

        Map<String, String> target = SourceTargetMapper.INSTANCE.sourceToTargetWithCustomLocale( source1 );
        assertThat( target  ).hasSize( 1 );
        assertThat( target ).contains( entry( "9,87E-18", "9,7E-9" ) );

        Map<BigDecimal, BigDecimal> source2 = SourceTargetMapper.INSTANCE.targetToSourceWithCustomLocale( target );
        assertThat( source2  ).hasSize( 1 );
        assertThat( source2 ).contains( entry( new BigDecimal( "987E-20" ), new BigDecimal( "97E-10" ) ) );

    }
}
