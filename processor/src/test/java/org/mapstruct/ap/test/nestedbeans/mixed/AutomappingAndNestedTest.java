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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishTankDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishTankWithNestedDocumentDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.MaterialDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.MaterialTypeDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.OrnamentDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterPlantDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityOrganisationDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityReportDto;
import org.mapstruct.ap.test.nestedbeans.mixed._target.WaterQualityWithDocumentDto;
import org.mapstruct.ap.test.nestedbeans.mixed.source.Fish;
import org.mapstruct.ap.test.nestedbeans.mixed.source.FishTank;
import org.mapstruct.ap.test.nestedbeans.mixed.source.Interior;
import org.mapstruct.ap.test.nestedbeans.mixed.source.MaterialType;
import org.mapstruct.ap.test.nestedbeans.mixed.source.Ornament;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterPlant;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterQuality;
import org.mapstruct.ap.test.nestedbeans.mixed.source.WaterQualityReport;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    FishDto.class,
    FishTankDto.class,
    WaterPlantDto.class,
    MaterialDto.class,
    MaterialTypeDto.class,
    OrnamentDto.class,
    WaterQualityDto.class,
    WaterQualityReportDto.class,
    WaterQualityOrganisationDto.class,
    Fish.class,
    FishTank.class,
    WaterPlant.class,
    MaterialType.class,
    Interior.class,
    Ornament.class,
    WaterQuality.class,
    WaterQualityReport.class,
    FishTankWithNestedDocumentDto.class,
    WaterQualityWithDocumentDto.class,
    FishTankMapper.class,
    FishTankMapperConstant.class,
    FishTankMapperExpression.class,
    FishTankMapperWithDocument.class
})
@IssueKey("1057")
@RunWith(AnnotationProcessorTestRunner.class)
public class AutomappingAndNestedTest {

    @Rule
    public GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        FishTankMapper.class,
        FishTankMapperConstant.class,
        FishTankMapperExpression.class,
        FishTankMapperWithDocument.class
    );

    @Test
    public void shouldAutomapAndHandleSourceAndTargetPropertyNesting() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankDto target = FishTankMapper.INSTANCE.map( source );

        // -- result
        assertThat( target.getName() ).isEqualTo( source.getName() );

        // fish and fishDto can be automapped
        assertThat( target.getFish() ).isNotNull();
        assertThat( target.getFish().getKind() ).isEqualTo( source.getFish().getType() );
        assertThat( target.getFish().getName() ).isNull();

        // automapping takes care of mapping property "waterPlant".
        assertThat( target.getPlant() ).isNotNull();
        assertThat( target.getPlant().getKind() ).isEqualTo( source.getPlant().getKind() );

        // ornament (nested asymetric source)
        assertThat( target.getOrnament() ).isNotNull();
        assertThat( target.getOrnament().getType() ).isEqualTo( source.getInterior().getOrnament().getType() );

        // material (nested asymetric target)
        assertThat( target.getMaterial() ).isNotNull();
        assertThat( target.getMaterial().getManufacturer() ).isNull();
        assertThat( target.getMaterial().getMaterialType() ).isNotNull();
        assertThat( target.getMaterial().getMaterialType().getType() ).isEqualTo( source.getMaterial().getType() );

        //  first symetric then asymetric
        assertThat( target.getQuality() ).isNotNull();
        assertThat( target.getQuality().getReport() ).isNotNull();
        assertThat( target.getQuality().getReport().getVerdict() )
            .isEqualTo( source.getQuality().getReport().getVerdict() );
        assertThat( target.getQuality().getReport().getOrganisation().getApproval() ).isNull();
        assertThat( target.getQuality().getReport().getOrganisation() ).isNotNull();
        assertThat( target.getQuality().getReport().getOrganisation().getName() )
            .isEqualTo( source.getQuality().getReport().getOrganisationName() );
    }

    @Test
    public void shouldAutomapAndHandleSourceAndTargetPropertyNestingReverse() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankDto target = FishTankMapper.INSTANCE.map( source );
        FishTank source2 = FishTankMapper.INSTANCE.map( target );

        // -- result
        assertThat( source2.getName() ).isEqualTo( source.getName() );

        // fish
        assertThat( source2.getFish() ).isNotNull();
        assertThat( source2.getFish().getType() ).isEqualTo( source.getFish().getType() );

        // interior, designer will not be mapped (asymetric) to target. Here it shows.
        assertThat( source2.getInterior() ).isNotNull();
        assertThat( source2.getInterior().getDesigner() ).isNull();
        assertThat( source2.getInterior().getOrnament() ).isNotNull();
        assertThat( source2.getInterior().getOrnament().getType() )
            .isEqualTo( source.getInterior().getOrnament().getType() );

        // material
        assertThat( source2.getMaterial() ).isNotNull();
        assertThat( source2.getMaterial().getType() ).isEqualTo( source.getMaterial().getType() );

        // plant
        assertThat( source2.getPlant().getKind() ).isEqualTo( source.getPlant().getKind() );

        // quality
        assertThat( source2.getQuality().getReport() ).isNotNull();
        assertThat( source2.getQuality().getReport().getOrganisationName() )
            .isEqualTo( source.getQuality().getReport().getOrganisationName() );
        assertThat( source2.getQuality().getReport().getVerdict() )
            .isEqualTo( source.getQuality().getReport().getVerdict() );
    }

    @Test
    public void shouldAutomapAndHandleSourceAndTargetPropertyNestingAndConstant() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankDto target = FishTankMapperConstant.INSTANCE.map( source );

        // -- result

        // fixed value
        assertThat( target.getFish().getName() ).isEqualTo( "Nemo" );

        // automapping takes care of mapping property "waterPlant".
        assertThat( target.getPlant() ).isNotNull();
        assertThat( target.getPlant().getKind() ).isEqualTo( source.getPlant().getKind() );

        // non-nested and constant
        assertThat( target.getMaterial() ).isNotNull();
        assertThat( target.getMaterial().getManufacturer() ).isEqualTo( "MMM" );
        assertThat( target.getMaterial().getMaterialType() ).isNotNull();
        assertThat( target.getMaterial().getMaterialType().getType() ).isEqualTo( source.getMaterial().getType() );

        assertThat( target.getOrnament() ).isNull();
        assertThat( target.getQuality() ).isNull();

    }

    @Test
    public void shouldAutomapAndHandleSourceAndTargetPropertyNestingAndExpresion() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankDto target = FishTankMapperExpression.INSTANCE.map( source );

        // -- result
        assertThat( target.getFish().getName() ).isEqualTo( "Jaws" );

        assertThat( target.getMaterial() ).isNull();
        assertThat( target.getOrnament() ).isNull();
        assertThat( target.getPlant() ).isNull();

        assertThat( target.getQuality() ).isNotNull();
        assertThat( target.getQuality().getReport() ).isNotNull();
        assertThat( target.getQuality().getReport().getVerdict() )
            .isEqualTo( source.getQuality().getReport().getVerdict() );
        assertThat( target.getQuality().getReport().getOrganisation() ).isNotNull();
        assertThat( target.getQuality().getReport().getOrganisation().getApproval() ).isNull();
        assertThat( target.getQuality().getReport().getOrganisation().getName() ).isEqualTo( "Dunno" );
    }

   @Test
    public void shouldAutomapIntermediateLevelAndMapConstant() {

        // -- prepare
        FishTank source = createFishTank();

        // -- action
        FishTankWithNestedDocumentDto target = FishTankMapperWithDocument.INSTANCE.map( source );

        // -- result
        assertThat( target.getFish().getName() ).isEqualTo( "Jaws" );

        assertThat( target.getMaterial() ).isNull();
        assertThat( target.getOrnament() ).isNull();
        assertThat( target.getPlant() ).isNull();

        assertThat( target.getQuality() ).isNotNull();
        assertThat( target.getQuality().getDocument() ).isNotNull();
        assertThat( target.getQuality().getDocument().getVerdict() )
            .isEqualTo( source.getQuality().getReport().getVerdict() );
        assertThat( target.getQuality().getDocument().getOrganisation() ).isNotNull();
        assertThat( target.getQuality().getDocument().getOrganisation().getApproval() ).isNull();
        assertThat( target.getQuality().getDocument().getOrganisation().getName() ).isEqualTo( "NoIdeaInc" );
    }

    private FishTank createFishTank() {
        FishTank fishTank = new FishTank();

        Fish fish = new Fish();
        fish.setType( "Carp" );

        WaterPlant waterplant = new WaterPlant();
        waterplant.setKind( "Water Hyacinth" );

        Interior interior = new Interior();
        interior.setDesigner( "MrVeryFamous" );
        Ornament ornament = new Ornament();
        ornament.setType( "castle" );
        interior.setOrnament( ornament );

        WaterQuality quality = new WaterQuality();
        WaterQualityReport report = new WaterQualityReport();
        report.setVerdict( "PASSED" );
        report.setOrganisationName( "ACME" );
        quality.setReport( report );

        MaterialType materialType = new MaterialType();
        materialType.setType( "myMaterialType" );

        fishTank.setName( "MyLittleFishTank" );
        fishTank.setFish( fish );
        fishTank.setPlant( waterplant );
        fishTank.setInterior( interior );
        fishTank.setMaterial( materialType );
        fishTank.setQuality( quality );

        return fishTank;
    }
}
