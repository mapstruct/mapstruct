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
package org.mapstruct.ap.test.globalmapping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Andreas Gudian
 *
 */
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( {
    BaseVehicleDto.class,
    BaseVehicleEntity.class,
    CarDto.class,
    CarEntity.class,
    CarMapper.class,
    DictionaryConfigHolder.class } )
public class GlobalMappingTest {

    @Test
    @IssueKey( "168" )
    public void globalMappingIsApplied() {
        CarDto carDto = new CarDto();
        carDto.setColour( "red" );
        carDto.setId( 42L );

        CarEntity carEntity = CarMapper.INSTANCE.toCarEntity( carDto );

        assertThat( carEntity.getColor() ).isEqualTo( "red" );
        assertThat( carEntity.getPrimaryKey() ).isEqualTo( 42L );
        assertThat( carEntity.getAuditTrail() ).isNull();
    }

    @Test
    @IssueKey( "168" )
    public void globalMappingIsOverriddenAtMethodLevel() {
        CarDto carDto = new CarDto();
        carDto.setColour( "red" );
        carDto.setId( 42L );

        CarEntity carEntity = CarMapper.INSTANCE.toCarEntityWithFixedAuditTrail( carDto );

        assertThat( carEntity.getColor() ).isEqualTo( "red" );
        assertThat( carEntity.getPrimaryKey() ).isEqualTo( 42L );
        assertThat( carEntity.getAuditTrail() ).isEqualTo( "fixed" );
    }

    @Test
    @IssueKey( "168" )
    public void globalMappingIsAppliedInReverse() {
        CarEntity carEntity = new CarEntity();
        carEntity.setColor( "red" );
        carEntity.setPrimaryKey( 42L );

        CarDto carDto = CarMapper.INSTANCE.toCarDto( carEntity );

        assertThat( carDto.getColour() ).isEqualTo( "red" );
        assertThat( carDto.getId() ).isEqualTo( 42L );
    }
}
