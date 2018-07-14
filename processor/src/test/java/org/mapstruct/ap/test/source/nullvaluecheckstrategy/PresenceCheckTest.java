/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.nullvaluecheckstrategy;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class PresenceCheckTest {

    @Test
    public void testCallingMappingMethodWithNullSource() {

        RockFestivalSource source =  new RockFestivalSource();
        RockFestivalTarget target = RockFestivalMapper.INSTANCE.map( source );
        assertThat( target.getStage() ).isNull();

        source.setArtistName( "New Order" );
        target = RockFestivalMapper.INSTANCE.map( source );
        assertThat( target.getStage() ).isEqualTo( Stage.THE_BARN );

   }

    @Test
    public void testCallingMappingMethodWithNullSourceWithConfig() {

        RockFestivalSource source =  new RockFestivalSource();
        RockFestivalTarget target = RockFestivalMapperWithConfig.INSTANCE.map( source );
        assertThat( target.getStage() ).isNull();

        source.setArtistName( "New Order" );
        target = RockFestivalMapperWithConfig.INSTANCE.map( source );
        assertThat( target.getStage() ).isEqualTo( Stage.THE_BARN );

   }

    @Test( expected = IllegalArgumentException.class )
    public void testCallingMappingMethodWithNullSourceOveridingConfig() {

        RockFestivalSource source =  new RockFestivalSource();
        RockFestivalMapperOveridingConfig.INSTANCE.map( source );
   }
}
