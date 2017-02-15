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
package org.mapstruct.ap.test.nestedbeans.mixed;

import javax.annotation.Generated;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishTankDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.MaterialDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.MaterialTypeDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterPlantDto;
import org.mapstruct.ap.test.nestedbeans.mixed.source.Fish;
import org.mapstruct.ap.test.nestedbeans.mixed.source.FishTank;
import org.mapstruct.ap.test.nestedbeans.mixed.source.MaterialType;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterPlant;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-02-19T16:25:03+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class FishTankMapperConstantImpl implements FishTankMapperConstant {

    @Override
    public FishTankDto map(FishTank source) {
        if ( source == null ) {
            return null;
        }

        FishTankDto fishTankDto = new FishTankDto();

        fishTankDto.setMaterial( fishTankToMaterialDto( source ) );
        fishTankDto.setFish( fishToFishDto( source.getFish() ) );
        fishTankDto.setPlant( waterPlantToWaterPlantDto( source.getPlant() ) );
        fishTankDto.setName( source.getName() );

        return fishTankDto;
    }

    protected MaterialTypeDto materialTypeToMaterialTypeDto(MaterialType materialType) {
        if ( materialType == null ) {
            return null;
        }

        MaterialTypeDto materialTypeDto = new MaterialTypeDto();

        materialTypeDto.setType( materialType.getType() );

        return materialTypeDto;
    }

    protected MaterialDto fishTankToMaterialDto(FishTank fishTank) {
        if ( fishTank == null ) {
            return null;
        }

        MaterialDto materialDto = new MaterialDto();

        materialDto.setMaterialType( materialTypeToMaterialTypeDto( fishTank.getMaterial() ) );

        materialDto.setManufacturer( "MMM" );

        return materialDto;
    }

    protected FishDto fishToFishDto(Fish fish) {
        if ( fish == null ) {
            return null;
        }

        FishDto fishDto = new FishDto();

        fishDto.setKind( fish.getType() );

        fishDto.setName( "Nemo" );

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
