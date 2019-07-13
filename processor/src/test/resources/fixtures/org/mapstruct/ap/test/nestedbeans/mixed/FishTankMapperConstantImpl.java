/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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

        fishTankDto.setFish( fishToFishDto( source.getFish() ) );
        fishTankDto.setMaterial( fishTankToMaterialDto( source ) );
        fishTankDto.setPlant( waterPlantToWaterPlantDto( source.getPlant() ) );
        fishTankDto.setName( source.getName() );

        return fishTankDto;
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

    protected WaterPlantDto waterPlantToWaterPlantDto(WaterPlant waterPlant) {
        if ( waterPlant == null ) {
            return null;
        }

        WaterPlantDto waterPlantDto = new WaterPlantDto();

        waterPlantDto.setKind( waterPlant.getKind() );

        return waterPlantDto;
    }
}
