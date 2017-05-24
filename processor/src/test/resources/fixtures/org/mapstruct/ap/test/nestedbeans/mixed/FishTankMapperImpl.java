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
import org.mapstruct.ap.test.nestedbeans.mixed._target.OrnamentDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterPlantDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityOrganisationDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityReportDto;
import org.mapstruct.ap.test.nestedbeans.mixed.source.Fish;
import org.mapstruct.ap.test.nestedbeans.mixed.source.FishTank;
import org.mapstruct.ap.test.nestedbeans.mixed.source.Interior;
import org.mapstruct.ap.test.nestedbeans.mixed.source.MaterialType;
import org.mapstruct.ap.test.nestedbeans.mixed.source.Ornament;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterPlant;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterQuality;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterQualityReport;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-04-24T17:26:46+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class FishTankMapperImpl implements FishTankMapper {

    @Override
    public FishTankDto map(FishTank source) {
        if ( source == null ) {
            return null;
        }

        FishTankDto fishTankDto = new FishTankDto();

        fishTankDto.setFish( fishToFishDto( source.getFish() ) );
        fishTankDto.setMaterial( fishTankToMaterialDto( source ) );
        fishTankDto.setQuality( waterQualityToWaterQualityDto( source.getQuality() ) );
        Ornament ornament = sourceInteriorOrnament( source );
        if ( ornament != null ) {
            fishTankDto.setOrnament( ornamentToOrnamentDto( ornament ) );
        }
        fishTankDto.setPlant( waterPlantToWaterPlantDto( source.getPlant() ) );
        fishTankDto.setName( source.getName() );

        return fishTankDto;
    }

    @Override
    public FishTankDto mapAsWell(FishTank source) {
        if ( source == null ) {
            return null;
        }

        FishTankDto fishTankDto = new FishTankDto();

        fishTankDto.setFish( fishToFishDto( source.getFish() ) );
        fishTankDto.setMaterial( fishTankToMaterialDto1( source ) );
        fishTankDto.setQuality( waterQualityToWaterQualityDto( source.getQuality() ) );
        Ornament ornament = sourceInteriorOrnament( source );
        if ( ornament != null ) {
            fishTankDto.setOrnament( ornamentToOrnamentDto( ornament ) );
        }
        fishTankDto.setPlant( waterPlantToWaterPlantDto( source.getPlant() ) );
        fishTankDto.setName( source.getName() );

        return fishTankDto;
    }

    @Override
    public FishTank map(FishTankDto source) {
        if ( source == null ) {
            return null;
        }

        FishTank fishTank = new FishTank();

        fishTank.setFish( fishDtoToFish( source.getFish() ) );
        fishTank.setQuality( waterQualityDtoToWaterQuality( source.getQuality() ) );
        fishTank.setInterior( fishTankDtoToInterior( source ) );
        MaterialTypeDto materialType = sourceMaterialMaterialType( source );
        if ( materialType != null ) {
            fishTank.setMaterial( materialTypeDtoToMaterialType( materialType ) );
        }
        fishTank.setPlant( waterPlantDtoToWaterPlant( source.getPlant() ) );
        fishTank.setName( source.getName() );

        return fishTank;
    }

    protected FishDto fishToFishDto(Fish fish) {
        if ( fish == null ) {
            return null;
        }

        FishDto fishDto = new FishDto();

        fishDto.setKind( fish.getType() );

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

        return materialDto;
    }

    protected WaterQualityOrganisationDto waterQualityReportToWaterQualityOrganisationDto(WaterQualityReport waterQualityReport) {
        if ( waterQualityReport == null ) {
            return null;
        }

        WaterQualityOrganisationDto waterQualityOrganisationDto = new WaterQualityOrganisationDto();

        waterQualityOrganisationDto.setName( waterQualityReport.getOrganisationName() );

        return waterQualityOrganisationDto;
    }

    protected WaterQualityReportDto waterQualityReportToWaterQualityReportDto(WaterQualityReport waterQualityReport) {
        if ( waterQualityReport == null ) {
            return null;
        }

        WaterQualityReportDto waterQualityReportDto = new WaterQualityReportDto();

        waterQualityReportDto.setOrganisation( waterQualityReportToWaterQualityOrganisationDto( waterQualityReport ) );
        waterQualityReportDto.setVerdict( waterQualityReport.getVerdict() );

        return waterQualityReportDto;
    }

    protected WaterQualityDto waterQualityToWaterQualityDto(WaterQuality waterQuality) {
        if ( waterQuality == null ) {
            return null;
        }

        WaterQualityDto waterQualityDto = new WaterQualityDto();

        waterQualityDto.setReport( waterQualityReportToWaterQualityReportDto( waterQuality.getReport() ) );

        return waterQualityDto;
    }

    private Ornament sourceInteriorOrnament(FishTank fishTank) {
        if ( fishTank == null ) {
            return null;
        }
        Interior interior = fishTank.getInterior();
        if ( interior == null ) {
            return null;
        }
        Ornament ornament = interior.getOrnament();
        if ( ornament == null ) {
            return null;
        }
        return ornament;
    }

    protected OrnamentDto ornamentToOrnamentDto(Ornament ornament) {
        if ( ornament == null ) {
            return null;
        }

        OrnamentDto ornamentDto = new OrnamentDto();

        ornamentDto.setType( ornament.getType() );

        return ornamentDto;
    }

    protected WaterPlantDto waterPlantToWaterPlantDto(WaterPlant waterPlant) {
        if ( waterPlant == null ) {
            return null;
        }

        WaterPlantDto waterPlantDto = new WaterPlantDto();

        waterPlantDto.setKind( waterPlant.getKind() );

        return waterPlantDto;
    }

    protected MaterialDto fishTankToMaterialDto1(FishTank fishTank) {
        if ( fishTank == null ) {
            return null;
        }

        MaterialDto materialDto = new MaterialDto();

        materialDto.setMaterialType( materialTypeToMaterialTypeDto( fishTank.getMaterial() ) );

        return materialDto;
    }

    protected WaterQualityOrganisationDto waterQualityReportToWaterQualityOrganisationDto1(WaterQualityReport waterQualityReport) {
        if ( waterQualityReport == null ) {
            return null;
        }

        WaterQualityOrganisationDto waterQualityOrganisationDto = new WaterQualityOrganisationDto();

        waterQualityOrganisationDto.setName( waterQualityReport.getOrganisationName() );

        return waterQualityOrganisationDto;
    }

    protected Fish fishDtoToFish(FishDto fishDto) {
        if ( fishDto == null ) {
            return null;
        }

        Fish fish = new Fish();

        fish.setType( fishDto.getKind() );

        return fish;
    }

    private String waterQualityReportDtoOrganisationName(WaterQualityReportDto waterQualityReportDto) {
        if ( waterQualityReportDto == null ) {
            return null;
        }
        WaterQualityOrganisationDto organisation = waterQualityReportDto.getOrganisation();
        if ( organisation == null ) {
            return null;
        }
        String name = organisation.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected WaterQualityReport waterQualityReportDtoToWaterQualityReport(WaterQualityReportDto waterQualityReportDto) {
        if ( waterQualityReportDto == null ) {
            return null;
        }

        WaterQualityReport waterQualityReport = new WaterQualityReport();

        String name = waterQualityReportDtoOrganisationName( waterQualityReportDto );
        if ( name != null ) {
            waterQualityReport.setOrganisationName( name );
        }
        waterQualityReport.setVerdict( waterQualityReportDto.getVerdict() );

        return waterQualityReport;
    }

    protected WaterQuality waterQualityDtoToWaterQuality(WaterQualityDto waterQualityDto) {
        if ( waterQualityDto == null ) {
            return null;
        }

        WaterQuality waterQuality = new WaterQuality();

        waterQuality.setReport( waterQualityReportDtoToWaterQualityReport( waterQualityDto.getReport() ) );

        return waterQuality;
    }

    protected Ornament ornamentDtoToOrnament(OrnamentDto ornamentDto) {
        if ( ornamentDto == null ) {
            return null;
        }

        Ornament ornament = new Ornament();

        ornament.setType( ornamentDto.getType() );

        return ornament;
    }

    protected Interior fishTankDtoToInterior(FishTankDto fishTankDto) {
        if ( fishTankDto == null ) {
            return null;
        }

        Interior interior = new Interior();

        interior.setOrnament( ornamentDtoToOrnament( fishTankDto.getOrnament() ) );

        return interior;
    }

    private MaterialTypeDto sourceMaterialMaterialType(FishTankDto fishTankDto) {
        if ( fishTankDto == null ) {
            return null;
        }
        MaterialDto material = fishTankDto.getMaterial();
        if ( material == null ) {
            return null;
        }
        MaterialTypeDto materialType = material.getMaterialType();
        if ( materialType == null ) {
            return null;
        }
        return materialType;
    }

    protected MaterialType materialTypeDtoToMaterialType(MaterialTypeDto materialTypeDto) {
        if ( materialTypeDto == null ) {
            return null;
        }

        MaterialType materialType = new MaterialType();

        materialType.setType( materialTypeDto.getType() );

        return materialType;
    }

    protected WaterPlant waterPlantDtoToWaterPlant(WaterPlantDto waterPlantDto) {
        if ( waterPlantDto == null ) {
            return null;
        }

        WaterPlant waterPlant = new WaterPlant();

        waterPlant.setKind( waterPlantDto.getKind() );

        return waterPlant;
    }
}
