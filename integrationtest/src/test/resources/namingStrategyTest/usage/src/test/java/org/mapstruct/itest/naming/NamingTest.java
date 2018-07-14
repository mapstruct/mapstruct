/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.naming;

import static org.assertj.core.api.Assertions.assertThat;

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
