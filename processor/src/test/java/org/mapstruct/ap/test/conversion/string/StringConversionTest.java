/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.string;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Source.class,
    Target.class,
    SourceTargetMapper.class
})
public class StringConversionTest {

    private static final String STRING_CONSTANT = "String constant";

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        SourceTargetMapper.class );

    @ProcessorTest
    public void shouldApplyStringConversions() {
        Source source = new Source();
        source.setB( (byte) 1 );
        source.setBb( (byte) 2 );
        source.setS( (short) 3 );
        source.setSs( (short) 4 );
        source.setI( 5 );
        source.setIi( 6 );
        source.setL( 7L );
        source.setLl( 8L );
        source.setF( 9f );
        source.setFf( 10f );
        source.setD( 11d );
        source.setDd( 12d );
        source.setBool( true );
        source.setBoolBool( Boolean.TRUE );
        source.setC( 'G' );
        source.setCc( 'H' );
        source.setSb( new StringBuilder( "SB" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( "1" );
        assertThat( target.getBb() ).isEqualTo( "2" );
        assertThat( target.getS() ).isEqualTo( "3" );
        assertThat( target.getSs() ).isEqualTo( "4" );
        assertThat( target.getI() ).isEqualTo( "5" );
        assertThat( target.getIi() ).isEqualTo( "6" );
        assertThat( target.getL() ).isEqualTo( "7" );
        assertThat( target.getLl() ).isEqualTo( "8" );
        assertThat( target.getF() ).isEqualTo( "9.0" );
        assertThat( target.getFf() ).isEqualTo( "10.0" );
        assertThat( target.getD() ).isEqualTo( "11.0" );
        assertThat( target.getDd() ).isEqualTo( "12.0" );
        assertThat( target.getBool() ).isEqualTo( "true" );
        assertThat( target.getBoolBool() ).isEqualTo( "true" );
        assertThat( target.getC() ).isEqualTo( "G" );
        assertThat( target.getCc() ).isEqualTo( "H" );
        assertThat( target.getSb() ).isEqualTo( "SB" );
    }

    @IssueKey("2846")
    @ProcessorTest
    public void shouldNotApplyStringConversionsWhenNull() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( "0" );
        assertThat( target.getBb() ).isNull();
        assertThat( target.getS() ).isEqualTo( "0" );
        assertThat( target.getSs() ).isNull();
        assertThat( target.getI() ).isEqualTo( "0" );
        assertThat( target.getIi() ).isNull();
        assertThat( target.getL() ).isEqualTo( "0" );
        assertThat( target.getLl() ).isNull();
        assertThat( target.getF() ).isEqualTo( "0.0" );
        assertThat( target.getFf() ).isNull();
        assertThat( target.getD() ).isEqualTo( "0.0" );
        assertThat( target.getDd() ).isNull();
        assertThat( target.getBool() ).isEqualTo( "false" );
        assertThat( target.getBoolBool() ).isNull();
        assertThat( target.getC() ).isEqualTo( String.valueOf( '\u0000' ) );
        assertThat( target.getCc() ).isNull();
        assertThat( target.getSb() ).isNull();
    }

    @ProcessorTest
    public void shouldApplyReverseStringConversions() {
        Target target = new Target();
        target.setB( "1" );
        target.setBb( "2" );
        target.setS( "3" );
        target.setSs( "4" );
        target.setI( "5" );
        target.setIi( "6" );
        target.setL( "7" );
        target.setLl( "8" );
        target.setF( "9.0" );
        target.setFf( "10.0" );
        target.setD( "11.0" );
        target.setDd( "12.0" );
        target.setBool( "true" );
        target.setBoolBool( "true" );
        target.setC( "G" );
        target.setCc( "H" );
        target.setSb( "SB" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getB() ).isEqualTo( (byte) 1 );
        assertThat( source.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( source.getS() ).isEqualTo( (short) 3 );
        assertThat( source.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( source.getI() ).isEqualTo( 5 );
        assertThat( source.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( source.getL() ).isEqualTo( 7 );
        assertThat( source.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( source.getF() ).isEqualTo( 9f );
        assertThat( source.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( source.getD() ).isEqualTo( 11d );
        assertThat( source.getDd() ).isEqualTo( Double.valueOf( 12d ) );
        assertThat( source.getBool() ).isEqualTo( true );
        assertThat( source.getBoolBool() ).isEqualTo( true );
        assertThat( source.getC() ).isEqualTo( 'G' );
        assertThat( source.getCc() ).isEqualTo( 'H' );
        assertThat( source.getSb().toString() ).isEqualTo( "SB" );
    }

    @ProcessorTest
    @IssueKey( "328" )
    public void stringShouldBeMappedToObjectByReference() {
        Target target = new Target();
        target.setObject( STRING_CONSTANT );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        // no conversion, no built-in method
        assertThat( source.getObject() ).isSameAs( STRING_CONSTANT );
    }
}
