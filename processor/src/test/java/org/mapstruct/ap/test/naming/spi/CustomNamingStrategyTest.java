/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test do demonstrate the usage of custom implementations of {@link AccessorNamingStrategy}.
 *
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({ GolfPlayer.class, GolfPlayerDto.class, GolfPlayerMapper.class })
@WithServiceImplementation(CustomAccessorNamingStrategy.class)
public class CustomNamingStrategyTest {
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
