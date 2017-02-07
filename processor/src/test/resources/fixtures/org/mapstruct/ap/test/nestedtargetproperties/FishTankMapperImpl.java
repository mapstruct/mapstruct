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
package org.mapstruct.ap.test.nestedtargetproperties;

import javax.annotation.Generated;
import org.mapstruct.ap.test.nestedtargetproperties._target.FishDto;
import org.mapstruct.ap.test.nestedtargetproperties._target.FishTankDto;
import org.mapstruct.ap.test.nestedtargetproperties._target.WaterPlantDto;
import org.mapstruct.ap.test.nestedtargetproperties.source.Fish;
import org.mapstruct.ap.test.nestedtargetproperties.source.FishTank;
import org.mapstruct.ap.test.nestedtargetproperties.source.WaterPlant;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-02-07T21:05:06+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class FishTankMapperImpl implements FishTankMapper {

    @Override
    public FishTankDto map(FishTank source) {
        if ( source == null ) {
            return null;
        }

        FishTankDto fishTankDto = new FishTankDto();

        fishTankDto.setFish( fishTankToFishDto( source ) );
        fishTankDto.setPlant( waterPlantToWaterPlantDto( source.getPlant() ) );

        return fishTankDto;
    }

    private String fishTankFishType(FishTank fishTank) {

        if ( fishTank == null ) {
            return null;
        }
        Fish fish = fishTank.getFish();
        if ( fish == null ) {
            return null;
        }
        String type = fish.getType();
        if ( type == null ) {
            return null;
        }
        return type;
    }

    protected FishDto fishTankToFishDto(FishTank fishTank) {
        if ( fishTank == null ) {
            return null;
        }

        FishDto fishDto = new FishDto();

        String type = fishTankFishType( fishTank );
        if ( type != null ) {
            fishDto.setKind( type );
        }

        return fishDto;
    }

    protected WaterPlantDto waterPlantToWaterPlantDto(WaterPlant waterPlant) {
        if ( waterPlant == null ) {
            return null;
        }

        WaterPlantDto waterPlantDto = new WaterPlantDto();

        waterPlantDto.setKind( waterPlant.getKind() );

        return waterPlantDto;
    }
}
