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
package org.mapstruct.ap.test.inheritfromconfig;

import static org.fest.assertions.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    BaseVehicleDto.class,
    BaseVehicleEntity.class,
    CarDto.class,
    CarEntity.class,
    CarMapperWithAutoInheritance.class,
    CarMapperWithExplicitInheritance.class,
    AutoInheritedConfig.class
})
@IssueKey("168")
public class InheritFromConfigTest {

    @Test
    public void autoInheritedMappingIsApplied() {
        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperWithAutoInheritance.INSTANCE.toCarEntity( carDto );

        assertEntity( carEntity );
    }

    @Test
    public void autoInheritedMappingIsAppliedForMappingTarget() {
        CarDto carDto = newTestDto();
        CarEntity carEntity = new CarEntity();

        CarMapperWithAutoInheritance.INSTANCE.intoCarEntityOnItsOwn( carDto, carEntity );

        assertEntity( carEntity );
    }

    @Test
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

    @Test
    public void autoInheritedMappingIsOverriddenAtMethodLevel() {
        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperWithAutoInheritance.INSTANCE.toCarEntityWithFixedAuditTrail( carDto );

        assertThat( carEntity.getColor() ).isEqualTo( "red" );
        assertThat( carEntity.getPrimaryKey() ).isEqualTo( 42L );
        assertThat( carEntity.getAuditTrail() ).isEqualTo( "fixed" );
    }

    @Test
    public void autoInheritedMappingIsAppliedInReverse() {
        CarEntity carEntity = new CarEntity();
        carEntity.setColor( "red" );
        carEntity.setPrimaryKey( 42L );

        CarDto carDto = CarMapperWithAutoInheritance.INSTANCE.toCarDto( carEntity );

        assertThat( carDto.getColour() ).isEqualTo( "red" );
        assertThat( carDto.getId() ).isEqualTo( 42L );
    }

    @Test
    public void explicitInheritedMappingIsAppliedInReverse() {
        CarEntity carEntity = new CarEntity();
        carEntity.setColor( "red" );
        carEntity.setPrimaryKey( 42L );

        CarDto carDto = CarMapperWithExplicitInheritance.INSTANCE.toCarDto( carEntity );

        assertThat( carDto.getColour() ).isEqualTo( "red" );
        assertThat( carDto.getId() ).isEqualTo( 42L );
    }

    @Test
    public void explicitInheritedMappingWithTwoLevelsIsOverriddenAtMethodLevel() {
        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperWithExplicitInheritance.INSTANCE.toCarEntityWithFixedAuditTrail( carDto );

        assertThat( carEntity.getColor() ).isEqualTo( "red" );
        assertThat( carEntity.getPrimaryKey() ).isEqualTo( 42L );
        assertThat( carEntity.getAuditTrail() ).isEqualTo( "fixed" );
    }

    @Test
    public void explicitInheritedMappingIsApplied() {
        CarDto carDto = newTestDto();

        CarEntity carEntity = CarMapperWithExplicitInheritance.INSTANCE.toCarEntity( carDto );

        assertEntity( carEntity );
    }

    @Test
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

    @Test
    @WithClasses({ Erroneous1Mapper.class, Erroneous1Config.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Erroneous1Mapper.class,
                kind = Kind.ERROR,
                line = 36,
                messageRegExp = "More than one configuration prototype method is applicable. Use @InheritConfiguration"
                    + " to select one of them explicitly:"
                    + " .*BaseVehicleEntity baseDtoToEntity\\(.*BaseVehicleDto dto\\),"
                    + " .*BaseVehicleEntity anythingToEntity\\(java.lang.Object anyting\\)\\."),
            @Diagnostic(type = Erroneous1Mapper.class,
                kind = Kind.WARNING,
                line = 36,
                messageRegExp = "Unmapped target properties: \"primaryKey, auditTrail\"\\."),
            @Diagnostic(type = Erroneous1Mapper.class,
                kind = Kind.ERROR,
                line = 42,
                messageRegExp = "More than one configuration prototype method is applicable. Use @InheritConfiguration"
                    + " to select one of them explicitly:"
                    + " .*BaseVehicleEntity baseDtoToEntity\\(.*BaseVehicleDto dto\\),"
                    + " .*BaseVehicleEntity anythingToEntity\\(java.lang.Object anyting\\)\\."),
            @Diagnostic(type = Erroneous1Mapper.class,
                kind = Kind.WARNING,
                line = 42,
                messageRegExp = "Unmapped target property: \"primaryKey\"\\.")
        }
    )
    public void erroneous1MultiplePrototypeMethodsMatch() {

    }

    @Test
    @WithClasses({ Erroneous2Mapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = Erroneous2Mapper.class,
                kind = Kind.ERROR,
                line = 38,
                messageRegExp = "Cycle detected while evaluating inherited configurations. Inheritance path:"
                    + " .*CarEntity toCarEntity1\\(.*CarDto carDto\\)"
                    + " -> .*CarEntity toCarEntity2\\(.*CarDto carDto\\)"
                    + " -> void toCarEntity3\\(.*CarDto carDto, @MappingTarget .*CarEntity entity\\)"
                    + " -> .*CarEntity toCarEntity1\\(.*CarDto carDto\\)")
        }
    )
    public void erroneous2InheritanceCycle() {

    }
}
