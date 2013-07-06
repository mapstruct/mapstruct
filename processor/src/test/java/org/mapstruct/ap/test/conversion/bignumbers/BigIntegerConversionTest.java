/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.math.BigInteger;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests conversions between {@link BigInteger} and numbers as well as String.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
public class BigIntegerConversionTest extends MapperTestBase {

    @Test
    @IssueKey("21")
    public void shouldApplyConversions() {
        Source source = new Source();
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

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

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
}
