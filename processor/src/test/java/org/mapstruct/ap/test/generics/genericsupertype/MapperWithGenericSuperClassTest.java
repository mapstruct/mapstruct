/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.genericsupertype;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Gunnar Morling
 */
@WithClasses({
    Vessel.class,
    VesselDto.class,
    SearchResult.class,
    MapperBase.class,
    VesselSearchResultMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class MapperWithGenericSuperClassTest {

    @Test
    public void canCreateImplementationForMapperWithGenericSuperClass() {
        Vessel vessel = new Vessel();
        vessel.setName( "Pacific Queen" );

        SearchResult<Vessel> vessels = new SearchResult<Vessel>();
        vessels.setValues( Arrays.asList( vessel ) );
        vessels.setSize( 1L );

        SearchResult<VesselDto> dtos = VesselSearchResultMapper.INSTANCE.vesselSearchResultToDto( vessels );

        assertThat( dtos.getValues() ).extracting( "name" ).containsExactly( "Pacific Queen" );
        assertThat( dtos.getSize() ).isEqualTo( 1L );
    }
}
