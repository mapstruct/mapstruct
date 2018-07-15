/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.iterable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "707" )
@WithClasses( {
    CityDto.class,
    CityEntity.class,
    RiverDto.class,
    RiverEntity.class,
    TopologyDto.class,
    TopologyEntity.class,
    TopologyFeatureDto.class,
    TopologyFeatureEntity.class,
    TopologyMapper.class
} )
@RunWith( AnnotationProcessorTestRunner.class )
public class IterableAndQualifiersTest {

    @Test
    public void testGenerationBasedOnQualifier() {

        TopologyDto topologyDto1 = new TopologyDto();
        List<TopologyFeatureDto> topologyFeatures1 = new ArrayList<TopologyFeatureDto>();
        RiverDto riverDto = new RiverDto();
        riverDto.setName( "Rhine" );
        riverDto.setLength( 5 );
        topologyFeatures1.add( riverDto );
        topologyDto1.setTopologyFeatures( topologyFeatures1 );

        TopologyEntity result1 = TopologyMapper.INSTANCE.mapTopologyAsRiver( topologyDto1 );
        assertThat( result1.getTopologyFeatures() ).hasSize( 1 );
        assertThat( result1.getTopologyFeatures().get( 0 ).getName() ).isEqualTo( "Rhine" );
        assertThat( result1.getTopologyFeatures().get( 0 ) ).isInstanceOf( RiverEntity.class );
        assertThat( ( (RiverEntity) result1.getTopologyFeatures().get( 0 ) ).getLength() ).isEqualTo( 5 );

        TopologyDto topologyDto2 = new TopologyDto();
        List<TopologyFeatureDto> topologyFeatures2 = new ArrayList<TopologyFeatureDto>();
        CityDto cityDto = new CityDto();
        cityDto.setName( "Amsterdam" );
        cityDto.setPopulation( 800000 );
        topologyFeatures2.add( cityDto );
        topologyDto2.setTopologyFeatures( topologyFeatures2 );

        TopologyEntity result2 = TopologyMapper.INSTANCE.mapTopologyAsCity( topologyDto2 );
        assertThat( result2.getTopologyFeatures() ).hasSize( 1 );
        assertThat( result2.getTopologyFeatures().get( 0 ).getName() ).isEqualTo( "Amsterdam" );
        assertThat( result2.getTopologyFeatures().get( 0 ) ).isInstanceOf( CityEntity.class );
        assertThat( ( (CityEntity) result2.getTopologyFeatures().get( 0 ) ).getPopulation() ).isEqualTo( 800000 );

    }
}
