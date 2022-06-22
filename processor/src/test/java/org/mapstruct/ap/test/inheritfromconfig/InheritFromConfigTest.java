/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

import static org.assertj.core.api.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Andreas Gudian
 */
@WithClasses({
    BaseVehicleDto.class,
    BaseVehicleEntity.class,
    CarDto.class,
    CarEntity.class,
    CarMapperWithAutoInheritance.class,
    CarMapperWithExplicitInheritance.class,
    AutoInheritedConfig.class,
    NotToBeUsedMapper.class
})
@IssueKey("168")
public class InheritFromConfigTest {

    @ProcessorTest
    public void autoInheritedMappingIsApplied() {
        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperWithAutoInheritance.INSTANCE.toCarEntity( carDto );

        assertEntity( carEntity );
    }

    @ProcessorTest
    public void autoInheritedMappingIsAppliedForMappingTarget() {
        CarDto carDto = newTestDto();
        CarEntity carEntity = new CarEntity();

        CarMapperWithAutoInheritance.INSTANCE.intoCarEntityOnItsOwn( carDto, carEntity );

        assertEntity( carEntity );
    }

    @ProcessorTest
    public void autoInheritedMappingIsAppliedForMappingTargetWithTwoStepInheritance() {
        CarDto carDto = newTestDto();
        CarEntity carEntity = new CarEntity();

        CarMapperWithAutoInheritance.INSTANCE.intoCarEntity( carDto, carEntity );

        assertEntity( carEntity );
    }

    private void assertEntity(CarEntity carEntity) {
        assertThat( carEntity.getColor() ).isEqualTo( "red" );
        assertThat( carEntity.getPrimaryKey() ).isEqualTo( 42L );
        assertThat( carEntity.getAuditTrail() ).isNull();
    }

    private CarDto newTestDto() {
        CarDto carDto = new CarDto();
        carDto.setColour( "red" );
        carDto.setId( 42L );
        return carDto;
    }

    @ProcessorTest
    public void autoInheritedMappingIsOverriddenAtMethodLevel() {
        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperWithAutoInheritance.INSTANCE.toCarEntityWithFixedAuditTrail( carDto );

        assertThat( carEntity.getColor() ).isEqualTo( "red" );
        assertThat( carEntity.getPrimaryKey() ).isEqualTo( 42L );
        assertThat( carEntity.getAuditTrail() ).isEqualTo( "fixed" );
    }

    @ProcessorTest
    public void autoInheritedMappingIsAppliedInReverse() {
        CarEntity carEntity = new CarEntity();
        carEntity.setColor( "red" );
        carEntity.setPrimaryKey( 42L );

        CarDto carDto = CarMapperWithAutoInheritance.INSTANCE.toCarDto( carEntity );

        assertThat( carDto.getColour() ).isEqualTo( "red" );
        assertThat( carDto.getId() ).isEqualTo( 42L );
    }

    @ProcessorTest
    public void explicitInheritedMappingIsAppliedInReverse() {
        CarEntity carEntity = new CarEntity();
        carEntity.setColor( "red" );
        carEntity.setPrimaryKey( 42L );

        CarDto carDto = CarMapperWithExplicitInheritance.INSTANCE.toCarDto( carEntity );

        assertThat( carDto.getColour() ).isEqualTo( "red" );
        assertThat( carDto.getId() ).isEqualTo( 42L );
    }

    @ProcessorTest
    @IssueKey( "1065" )
    @WithClasses({ CarMapperReverseWithExplicitInheritance.class } )
    public void explicitInheritedMappingIsAppliedInReverseDirectlyFromConfig() {

        CarEntity carEntity = new CarEntity();
        carEntity.setColor( "red" );
        carEntity.setPrimaryKey( 42L );

        CarDto carDto = CarMapperReverseWithExplicitInheritance.INSTANCE.toCarDto( carEntity );

        assertThat( carDto.getColour() ).isEqualTo( "red" );
        assertThat( carDto.getId() ).isEqualTo( 42L );
    }

    @ProcessorTest
    @IssueKey( "1255" )
    @WithClasses({ CarMapperReverseWithAutoInheritance.class, AutoInheritedReverseConfig.class } )
    public void autoInheritedMappingIsAppliedInReverseDirectlyFromConfig() {

        CarEntity carEntity = new CarEntity();
        carEntity.setColor( "red" );
        carEntity.setPrimaryKey( 42L );

        CarDto carDto = CarMapperReverseWithAutoInheritance.INSTANCE.toCarDto( carEntity );

        assertThat( carDto.getColour() ).isEqualTo( "red" );
        assertThat( carDto.getId() ).isEqualTo( 42L );
    }

    @ProcessorTest
    @IssueKey( "1255" )
    @WithClasses({ CarMapperAllWithAutoInheritance.class, AutoInheritedAllConfig.class } )
    public void autoInheritedMappingIsAppliedInForwardAndReverseDirectlyFromConfig() {

        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperAllWithAutoInheritance.INSTANCE.toCarEntity( carDto );
        CarDto carDto2 =  CarMapperAllWithAutoInheritance.INSTANCE.toCarDto( carEntity );

        assertThat( carDto.getColour() ).isEqualTo( carDto2.getColour() );
        assertThat( carDto.getId() ).isEqualTo( carDto2.getId() );
    }

    @ProcessorTest
    public void explicitInheritedMappingWithTwoLevelsIsOverriddenAtMethodLevel() {
        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperWithExplicitInheritance.INSTANCE.toCarEntityWithFixedAuditTrail( carDto );

        assertThat( carEntity.getColor() ).isEqualTo( "red" );
        assertThat( carEntity.getPrimaryKey() ).isEqualTo( 42L );
        assertThat( carEntity.getAuditTrail() ).isEqualTo( "fixed" );
    }

    @ProcessorTest
    public void explicitInheritedMappingIsApplied() {
        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperWithExplicitInheritance.INSTANCE.toCarEntity( carDto );

        assertEntity( carEntity );
    }

    @ProcessorTest
    @WithClasses({
        DriverDto.class,
        CarWithDriverEntity.class,
        CarWithDriverMapperWithAutoInheritance.class,
        AutoInheritedDriverConfig.class
    })
    public void autoInheritedFromMultipleSources() {
        CarDto carDto = newTestDto();
        DriverDto driverDto = new DriverDto();
        driverDto.setName( "Malcroft" );

        CarWithDriverEntity carWithDriverEntity =
            CarWithDriverMapperWithAutoInheritance.INSTANCE.toCarWithDriverEntity( carDto, driverDto );

        assertEntity( carWithDriverEntity );
        assertThat( carWithDriverEntity.getDriverName() ).isEqualTo( "Malcroft" );
    }

    @ProcessorTest
    @WithClasses({ Erroneous1Mapper.class, Erroneous1Config.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Erroneous1Mapper.class,
                kind = Kind.ERROR,
                line = 23,
                message = "More than one configuration prototype method is applicable. Use @InheritConfiguration to " +
                    "select one of them explicitly: org.mapstruct.ap.test.inheritfromconfig.BaseVehicleEntity " +
                    "baseDtoToEntity(org.mapstruct.ap.test.inheritfromconfig.BaseVehicleDto dto), org.mapstruct.ap" +
                    ".test.inheritfromconfig.BaseVehicleEntity anythingToEntity(java.lang.Object anyting)."),
            @Diagnostic(type = Erroneous1Mapper.class,
                kind = Kind.WARNING,
                line = 23,
                message = "Unmapped target properties: \"primaryKey, auditTrail\"."),
            @Diagnostic(type = Erroneous1Mapper.class,
                kind = Kind.ERROR,
                line = 29,
                message = "More than one configuration prototype method is applicable. Use @InheritConfiguration to " +
                    "select one of them explicitly: org.mapstruct.ap.test.inheritfromconfig.BaseVehicleEntity " +
                    "baseDtoToEntity(org.mapstruct.ap.test.inheritfromconfig.BaseVehicleDto dto), org.mapstruct.ap" +
                    ".test.inheritfromconfig.BaseVehicleEntity anythingToEntity(java.lang.Object anyting)."),
            @Diagnostic(type = Erroneous1Mapper.class,
                kind = Kind.WARNING,
                line = 29,
                message = "Unmapped target property: \"primaryKey\".")
        }
    )
    public void erroneous1MultiplePrototypeMethodsMatch() {
    }

    @ProcessorTest
    @WithClasses({ Erroneous2Mapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Erroneous2Mapper.class,
                kind = Kind.ERROR,
                line = 25,
                message = "Cycle detected while evaluating inherited configurations. Inheritance path: org.mapstruct" +
                    ".ap.test.inheritfromconfig.CarEntity toCarEntity1(org.mapstruct.ap.test.inheritfromconfig.CarDto" +
                    " carDto) -> org.mapstruct.ap.test.inheritfromconfig.CarEntity toCarEntity2(org.mapstruct.ap.test" +
                    ".inheritfromconfig.CarDto carDto) -> void toCarEntity3(org.mapstruct.ap.test.inheritfromconfig" +
                    ".CarDto carDto, @MappingTarget org.mapstruct.ap.test.inheritfromconfig.CarEntity entity) -> org" +
                    ".mapstruct.ap.test.inheritfromconfig.CarEntity toCarEntity1(org.mapstruct.ap.test" +
                    ".inheritfromconfig.CarDto carDto)")
        }
    )
    public void erroneous2InheritanceCycle() {
    }

    @ProcessorTest
    @IssueKey( "1255" )
    @WithClasses({ ErroneousMapperAutoInheritance.class, AutoInheritedReverseConfig.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapperAutoInheritance.class,
                kind = Kind.ERROR,
                line = 22,
                message = "Unmapped target properties: \"primaryKey, auditTrail\".")
        }
    )
    public void erroneousWrongReverseConfigInherited() { }

    @ProcessorTest
    @IssueKey( "1255" )
    @WithClasses({ ErroneousMapperReverseWithAutoInheritance.class, AutoInheritedConfig.class } )
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousMapperReverseWithAutoInheritance.class,
                kind = Kind.ERROR,
                line = 23,
                message = "Unmapped target property: \"id\".")
        }
    )
    public void erroneousWrongConfigInherited() { }

    @ProcessorTest
    @IssueKey("1255")
    @WithClasses({ Erroneous3Mapper.class, Erroneous3Config.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Erroneous3Mapper.class,
                kind = Kind.ERROR,
                line = 22,
                message = "More than one configuration prototype method is applicable. Use " +
                    "@InheritInverseConfiguration to select one of them explicitly: org.mapstruct.ap.test" +
                    ".inheritfromconfig.BaseVehicleEntity baseDtoToEntity(org.mapstruct.ap.test.inheritfromconfig" +
                    ".BaseVehicleDto dto), org.mapstruct.ap.test.inheritfromconfig.BaseVehicleEntity baseDtoToEntity2" +
                    "(org.mapstruct.ap.test.inheritfromconfig.BaseVehicleDto dto)."),
            @Diagnostic(type = Erroneous3Mapper.class,
                kind = Kind.ERROR,
                line = 22,
                message = "Unmapped target property: \"id\".")
        }
    )
    public void erroneousDuplicateReverse() {
    }

}
