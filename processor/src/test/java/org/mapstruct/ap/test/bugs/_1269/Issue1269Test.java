/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1269;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._1269.dto.VehicleDto;
import org.mapstruct.ap.test.bugs._1269.dto.VehicleImageDto;
import org.mapstruct.ap.test.bugs._1269.dto.VehicleInfoDto;
import org.mapstruct.ap.test.bugs._1269.mapper.VehicleMapper;
import org.mapstruct.ap.test.bugs._1269.model.Vehicle;
import org.mapstruct.ap.test.bugs._1269.model.VehicleImage;
import org.mapstruct.ap.test.bugs._1269.model.VehicleTypeInfo;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey( "1269" )
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( {
    VehicleDto.class,
    VehicleImageDto.class,
    VehicleInfoDto.class,
    Vehicle.class,
    VehicleImage.class,
    VehicleTypeInfo.class,
    VehicleMapper.class
} )
public class Issue1269Test {

    @Test
    public void shouldMapNestedPropertiesCorrectly() {

        VehicleTypeInfo sourceTypeInfo = new VehicleTypeInfo( "Opel", "Corsa", 3 );

        List<VehicleImage> sourceImages = Arrays.asList(
            new VehicleImage( 100, "something" ),
            new VehicleImage( 150, "somethingElse" )
        );
        Vehicle source = new Vehicle( sourceTypeInfo, sourceImages );

        VehicleDto target = VehicleMapper.INSTANCE.map( source );

        assertThat( target.getVehicleInfo() ).isNotNull();
        assertThat( target.getVehicleInfo().getDoors() ).isEqualTo( 3 );
        assertThat( target.getVehicleInfo().getType() ).isEqualTo( "Opel" );
        assertThat( target.getVehicleInfo().getName() ).isEqualTo( "Corsa" );
        assertThat( target.getVehicleInfo().getImages() ).hasSize( 2 );
        assertThat( target.getVehicleInfo().getImages().get( 0 ) )
            .isEqualToComparingFieldByField( sourceImages.get( 0 ) );
        assertThat( target.getVehicleInfo().getImages().get( 1 ) )
            .isEqualToComparingFieldByField( sourceImages.get( 1 ) );
    }
}
