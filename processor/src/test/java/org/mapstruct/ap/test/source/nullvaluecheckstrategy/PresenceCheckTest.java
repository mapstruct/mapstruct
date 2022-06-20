/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.nullvaluecheckstrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    RockFestivalMapper.class,
    RockFestivalSource.class,
    RockFestivalTarget.class,
    RockFestivalMapperConfig.class,
    RockFestivalMapperWithConfig.class,
    RockFestivalMapperOveridingConfig.class,
    Stage.class
})
public class PresenceCheckTest {

    @ProcessorTest
    public void testCallingMappingMethodWithNullSource() {

        RockFestivalSource source =  new RockFestivalSource();
        RockFestivalTarget target = RockFestivalMapper.INSTANCE.map( source );
        assertThat( target.getStage() ).isNull();

        source.setArtistName( "New Order" );
        target = RockFestivalMapper.INSTANCE.map( source );
        assertThat( target.getStage() ).isEqualTo( Stage.THE_BARN );

   }

    @ProcessorTest
    public void testCallingMappingMethodWithNullSourceWithConfig() {

        RockFestivalSource source =  new RockFestivalSource();
        RockFestivalTarget target = RockFestivalMapperWithConfig.INSTANCE.map( source );
        assertThat( target.getStage() ).isNull();

        source.setArtistName( "New Order" );
        target = RockFestivalMapperWithConfig.INSTANCE.map( source );
        assertThat( target.getStage() ).isEqualTo( Stage.THE_BARN );

   }

    @ProcessorTest
    public void testCallingMappingMethodWithNullSourceOveridingConfig() {

        RockFestivalSource source =  new RockFestivalSource();
        assertThatThrownBy( () -> RockFestivalMapperOveridingConfig.INSTANCE.map( source ) )
            .isInstanceOf( IllegalArgumentException.class );
   }
}
