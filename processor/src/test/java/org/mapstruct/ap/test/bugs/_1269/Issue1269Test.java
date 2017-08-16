/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
