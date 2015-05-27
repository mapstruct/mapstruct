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
package org.mapstruct.itest.naming;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.itest.naming.GolfPlayer;
import org.mapstruct.itest.naming.GolfPlayerDto;
import org.mapstruct.itest.naming.GolfPlayerMapper;

/**
 * Test for using a custom naming strategy.
 *
 * @author Gunnar Morling
 */
public class NamingTest {

    @Test
    public void shouldApplyCustomNamingStrategy() {
        GolfPlayer player = new GolfPlayer()
            .withName( "Jared" )
            .withHandicap( 9.2D );

        GolfPlayerDto dto = GolfPlayerMapper.INSTANCE.golfPlayerToDto( player );

        assertThat( dto ).isNotNull();
        assertThat( dto.name() ).isEqualTo( "Jared" );
        assertThat( dto.handicap() ).isEqualTo( 9.2D );
    }
}
