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
package org.mapstruct.ap.test.conversion.string;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

@WithClasses({
    Source.class,
    Target.class,
    SourceTargetMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class StringConversionTest {

    private static final String STRING_CONSTANT = "String constant";

    @Test
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
    }

    @Test
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
    }

    @Test
    @IssueKey( "328" )
    public void stringShouldBeMappedToObjectByReference() {
        Target target = new Target();
        target.setObject( STRING_CONSTANT );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        // no conversion, no built-in method
        assertThat( source.getObject() ).isSameAs( STRING_CONSTANT );
    }
}
