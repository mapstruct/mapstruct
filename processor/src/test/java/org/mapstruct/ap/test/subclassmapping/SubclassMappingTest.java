/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.ap.test.subclassmapping.mappables.Bike;
import org.mapstruct.ap.test.subclassmapping.mappables.BikeDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Car;
import org.mapstruct.ap.test.subclassmapping.mappables.CarDto;
import org.mapstruct.ap.test.subclassmapping.mappables.HatchBack;
import org.mapstruct.ap.test.subclassmapping.mappables.HatchBackDto;
import org.mapstruct.ap.test.subclassmapping.mappables.Vehicle;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleCollection;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleCollectionDto;
import org.mapstruct.ap.test.subclassmapping.mappables.VehicleDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey( "131" )
@WithClasses( { SubclassMapperUsingExistingMappings.class, SimpleSubclassMapper.class, VehicleCollection.class,
    Vehicle.class, Car.class, Bike.class, VehicleCollectionDto.class, VehicleDto.class, CarDto.class, BikeDto.class } )
public class SubclassMappingTest {

    @ProcessorTest
    void mappingIsDoneUsingSubclassMappingTest() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Bike() );

        VehicleCollectionDto result = SimpleSubclassMapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
                                          .extracting( vehicle -> (Class) vehicle.getClass() )
                                          .containsExactly( CarDto.class, BikeDto.class );
    }

    @ProcessorTest
    void existingMappingsAreUsedWhenFoundTest() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );

        VehicleCollectionDto result = SubclassMapperUsingExistingMappings.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() )
                                          .extracting( VehicleDto::getName )
                                          .containsExactly( "created through existing mapping." );
    }

    @ProcessorTest
    void subclassMappingInheritsMappingTest() {
        VehicleCollection vehicles = new VehicleCollection();
        Car car = new Car();
        car.setVehicleManufacturingCompany( "BenZ" );
        vehicles.getVehicles().add( car );

        VehicleCollectionDto result = SimpleSubclassMapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() )
                                          .extracting( VehicleDto::getMaker )
                                          .containsExactly( "BenZ" );
    }

    @ProcessorTest
    @WithClasses( { SubclassOrderWarningMapper.class, HatchBack.class, HatchBackDto.class } )
    @ExpectedCompilationOutcome( value = CompilationResult.SUCCEEDED, diagnostics = {
        @Diagnostic( type = SubclassOrderWarningMapper.class,
                     kind = javax.tools.Diagnostic.Kind.WARNING,
                     line = 28,
                     alternativeLine = 30,
                     message = "SubclassMapping annotation for "
                            + "'org.mapstruct.ap.test.subclassmapping.mappables.HatchBackDto' found after "
                            + "'org.mapstruct.ap.test.subclassmapping.mappables.CarDto', but all "
                            + "'org.mapstruct.ap.test.subclassmapping.mappables.HatchBackDto' "
                            + "objects are also instances of "
                            + "'org.mapstruct.ap.test.subclassmapping.mappables.CarDto'." ) } )
    void subclassOrderWarningTest() { }

    @ProcessorTest
    @WithClasses( { ErroneousSubclassMapper2.class } )
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic( type = ErroneousSubclassMapper2.class,
                     kind = javax.tools.Diagnostic.Kind.ERROR,
                     line = 21,
                     message = "SubclassMapping annotation can not be used for update methods."
                   ),
        @Diagnostic( type = ErroneousSubclassMapper2.class,
                     kind = javax.tools.Diagnostic.Kind.ERROR,
                     line = 25,
                     message = "SubclassMapping annotation can not be used for update methods."
                   ) } )
    void unsupportedMethodTest() { }

    @ProcessorTest
    @WithClasses( { ErroneousSubclassMapper1.class } )
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic( type = ErroneousSubclassMapper1.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 21,
                        message = "Could not find a parameter that is a superclass for "
                            + "'org.mapstruct.ap.test.subclassmapping.mappables.Bike'."
                        ),
        @Diagnostic( type = ErroneousSubclassMapper1.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 21,
                        message = "Class 'org.mapstruct.ap.test.subclassmapping.mappables.CarDto'"
                            + " is not a subclass of "
                            + "'org.mapstruct.ap.test.subclassmapping.mappables.BikeDto'."
                        ) } )
    void erroneousMethodTest() { }
}
