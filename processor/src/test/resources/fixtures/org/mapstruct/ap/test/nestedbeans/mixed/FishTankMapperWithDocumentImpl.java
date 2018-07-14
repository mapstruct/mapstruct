/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.mixed;

import javax.annotation.Generated;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishTankWithNestedDocumentDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityOrganisationDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityReportDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityWithDocumentDto;
import org.mapstruct.ap.test.nestedbeans.mixed.source.Fish;
import org.mapstruct.ap.test.nestedbeans.mixed.source.FishTank;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterQuality;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterQualityReport;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-02-19T16:25:03+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class FishTankMapperWithDocumentImpl implements FishTankMapperWithDocument {

    @Override
    public FishTankWithNestedDocumentDto map(FishTank source) {
        if ( source == null ) {
            return null;
        }

        FishTankWithNestedDocumentDto fishTankWithNestedDocumentDto = new FishTankWithNestedDocumentDto();

        fishTankWithNestedDocumentDto.setFish( fishToFishDto( source.getFish() ) );
        fishTankWithNestedDocumentDto.setQuality( waterQualityToWaterQualityWithDocumentDto( source.getQuality() ) );
        fishTankWithNestedDocumentDto.setName( source.getName() );

        return fishTankWithNestedDocumentDto;
    }

    protected FishDto fishToFishDto(Fish fish) {
        if ( fish == null ) {
            return null;
        }

        FishDto fishDto = new FishDto();

        fishDto.setKind( fish.getType() );

        fishDto.setName( "Jaws" );

        return fishDto;
    }

    protected WaterQualityOrganisationDto waterQualityReportToWaterQualityOrganisationDto(WaterQualityReport waterQualityReport) {
        if ( waterQualityReport == null ) {
            return null;
        }

        WaterQualityOrganisationDto waterQualityOrganisationDto = new WaterQualityOrganisationDto();

        waterQualityOrganisationDto.setName( "NoIdeaInc" );

        return waterQualityOrganisationDto;
    }

    protected WaterQualityReportDto waterQualityReportToWaterQualityReportDto(WaterQualityReport waterQualityReport) {
        if ( waterQualityReport == null ) {
            return null;
        }

        WaterQualityReportDto waterQualityReportDto = new WaterQualityReportDto();

        waterQualityReportDto.setVerdict( waterQualityReport.getVerdict() );
        waterQualityReportDto.setOrganisation( waterQualityReportToWaterQualityOrganisationDto( waterQualityReport ) );

        return waterQualityReportDto;
    }

    protected WaterQualityWithDocumentDto waterQualityToWaterQualityWithDocumentDto(WaterQuality waterQuality) {
        if ( waterQuality == null ) {
            return null;
        }

        WaterQualityWithDocumentDto waterQualityWithDocumentDto = new WaterQualityWithDocumentDto();

        waterQualityWithDocumentDto.setDocument( waterQualityReportToWaterQualityReportDto( waterQuality.getReport() ) );

        return waterQualityWithDocumentDto;
    }
}
