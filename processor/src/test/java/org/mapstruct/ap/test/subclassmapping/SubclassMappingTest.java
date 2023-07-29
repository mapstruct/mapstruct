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

@IssueKey("131")
@WithClasses({
    Bike.class,
    BikeDto.class,
    Car.class,
    CarDto.class,
    VehicleCollection.class,
    VehicleCollectionDto.class,
    Vehicle.class,
    VehicleDto.class,
})
public class SubclassMappingTest {

    @ProcessorTest
    @WithClasses( SimpleSubclassMapper.class )
    void mappingIsDoneUsingSubclassMapping() {
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
    @WithClasses( DeepCloneMapper.class )
    void deepCloneMappingClonesObjects() {
        Car car = new Car();
        car.setManual( true );
        car.setName( "namedCar" );
        car.setVehicleManufacturingCompany( "veMac" );

        Vehicle result = DeepCloneMapper.INSTANCE.map( car );

        assertThat( result ).isInstanceOf( Car.class );
        assertThat( result ).isNotSameAs( car );
        assertThat( result ).usingRecursiveComparison().isEqualTo( car );
    }

    @ProcessorTest
    @WithClasses( DeepCloneMethodMapper.class )
    void deepCloneMappingOnMethodClonesObjects() {
        Car car = new Car();
        car.setManual( true );
        car.setName( "namedCar" );
        car.setVehicleManufacturingCompany( "veMac" );

        Vehicle result = DeepCloneMethodMapper.INSTANCE.map( car );

        assertThat( result ).isInstanceOf( Car.class );
        assertThat( result ).isNotSameAs( car );
        assertThat( result ).usingRecursiveComparison().isEqualTo( car );
    }

    @ProcessorTest
    @WithClasses( SimpleSubclassMapper.class )
    void inverseMappingIsDoneUsingSubclassMapping() {
        VehicleCollectionDto vehicles = new VehicleCollectionDto();
        vehicles.getVehicles().add( new CarDto() );
        vehicles.getVehicles().add( new BikeDto() );

        VehicleCollection result = SimpleSubclassMapper.INSTANCE.mapInverse( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly( Car.class, Bike.class );
    }

    @ProcessorTest
    @WithClasses( SubclassMapperUsingExistingMappings.class )
    void existingMappingsAreUsedWhenFound() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );

        VehicleCollectionDto result = SubclassMapperUsingExistingMappings.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() )
            .extracting( VehicleDto::getName )
            .containsExactly( "created through existing mapping." );
    }

    @ProcessorTest
    @WithClasses( SimpleSubclassMapper.class )
    void subclassMappingInheritsInverseMapping() {
        VehicleCollectionDto vehiclesDto = new VehicleCollectionDto();
        CarDto carDto = new CarDto();
        carDto.setMaker( "BenZ" );
        vehiclesDto.getVehicles().add( carDto );

        VehicleCollection result = SimpleSubclassMapper.INSTANCE.mapInverse( vehiclesDto );

        assertThat( result.getVehicles() )
            .extracting( Vehicle::getVehicleManufacturingCompany )
            .containsExactly( "BenZ" );
    }

    @ProcessorTest
    @WithClasses( {
        HatchBack.class,
        InverseOrderSubclassMapper.class
    } )
    void subclassMappingOverridesInverseInheritedMapping() {
        VehicleCollectionDto vehicleDtos = new VehicleCollectionDto();
        CarDto carDto = new CarDto();
        carDto.setMaker( "BenZ" );
        vehicleDtos.getVehicles().add( carDto );

        VehicleCollection result = InverseOrderSubclassMapper.INSTANCE.mapInverse( vehicleDtos );

        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly( Car.class );
    }

    @IssueKey( "3125" )
    @ProcessorTest
    @WithClasses( {
        HatchBack.class,
        InheritedSubclassMapper.class
    } )
    void subclassMappingOverridesInheritedMapping() {
        Vehicle bike = new Bike();

        VehicleDto result = InheritedSubclassMapper.INSTANCE.map( bike );
        VehicleDto resultInherited = InheritedSubclassMapper.INSTANCE.mapInherited( bike );
        VehicleDto resultOverride = InheritedSubclassMapper.INSTANCE.mapInheritedOverride( bike );

        assertThat( result ).isInstanceOf( BikeDto.class );
        assertThat( resultInherited ).isInstanceOf( BikeDto.class );
        assertThat( resultOverride ).isInstanceOf( CarDto.class );
    }

    @ProcessorTest
    @WithClasses( { SubclassCompositeMapper.class, CompositeSubclassMappingAnnotation.class })
    void mappingIsDoneUsingSubclassMappingWithCompositeMapping() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Bike() );

        VehicleCollectionDto result = SubclassCompositeMapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly( CarDto.class, BikeDto.class );
    }

    @ProcessorTest
    @WithClasses( { SubclassCompositeMapper.class, CompositeSubclassMappingAnnotation.class })
    void inverseMappingIsDoneUsingSubclassMappingWithCompositeMapping() {
        VehicleCollectionDto vehicles = new VehicleCollectionDto();
        vehicles.getVehicles().add( new CarDto() );
        vehicles.getVehicles().add( new BikeDto() );

        VehicleCollection result = SubclassCompositeMapper.INSTANCE.mapInverse( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly( Car.class, Bike.class );
    }

    @ProcessorTest
    @WithClasses( { SubclassCompositeMapper.class, CompositeSubclassMappingAnnotation.class })
    void subclassMappingInheritsInverseMappingWithCompositeMapping() {
        VehicleCollectionDto vehiclesDto = new VehicleCollectionDto();
        CarDto carDto = new CarDto();
        carDto.setMaker( "BenZ" );
        vehiclesDto.getVehicles().add( carDto );

        VehicleCollection result = SubclassCompositeMapper.INSTANCE.mapInverse( vehiclesDto );

        assertThat( result.getVehicles() )
            .extracting( Vehicle::getVehicleManufacturingCompany )
            .containsExactly( "BenZ" );
    }

    @ProcessorTest
    @WithClasses({
        HatchBack.class,
        HatchBackDto.class,
        SubclassOrderWarningMapper.class,
    })
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED, diagnostics = {
        @Diagnostic(type = SubclassOrderWarningMapper.class,
            kind = javax.tools.Diagnostic.Kind.WARNING,
            line = 28,
            alternativeLine = 30,
            message = "SubclassMapping annotation for "
                + "'org.mapstruct.ap.test.subclassmapping.mappables.HatchBack' found after "
                + "'org.mapstruct.ap.test.subclassmapping.mappables.Car', but all "
                + "'org.mapstruct.ap.test.subclassmapping.mappables.HatchBack' "
                + "objects are also instances of "
                + "'org.mapstruct.ap.test.subclassmapping.mappables.Car'.")
    })
    void subclassOrderWarning() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousSubclassUpdateMapper.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(type = ErroneousSubclassUpdateMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 21,
            message = "SubclassMapping annotation can not be used for update methods."
        ),
        @Diagnostic(type = ErroneousSubclassUpdateMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 25,
            message = "SubclassMapping annotation can not be used for update methods."
        )
    })
    void unsupportedUpdateMethod() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousSubclassMapper1.class })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(type = ErroneousSubclassMapper1.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 21,
            message = "Could not find a parameter that is a superclass for "
                + "'org.mapstruct.ap.test.subclassmapping.mappables.Bike'."
        ),
        @Diagnostic(type = ErroneousSubclassMapper1.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 21,
            message = "Class 'org.mapstruct.ap.test.subclassmapping.mappables.CarDto'"
                + " is not a subclass of "
                + "'org.mapstruct.ap.test.subclassmapping.mappables.BikeDto'."
        )
    })
    void erroneousMethodWithSourceTargetType() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousInverseSubclassMapper.class })
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(type = ErroneousInverseSubclassMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 28,
            message = "Subclass "
                + "'org.mapstruct.ap.test.subclassmapping.mappables.VehicleDto'"
                + " is already defined as a source."
        )
    })
    void inverseSubclassMappingNotPossible() {
    }

    @ProcessorTest
    @WithClasses(SubclassIgnoreByDefaultMapper.class)
    void beanMappingIgnoreByDefaultShouldBePropagated() {
        Car car = new Car();
        car.setName( "Test car" );
        car.setManual( true );
        VehicleDto target = SubclassIgnoreByDefaultMapper.INSTANCE.map( car );
        assertThat( target )
            .isInstanceOfSatisfying( CarDto.class, carDto -> {
                assertThat( carDto.getName() ).isEqualTo( "Test car" );
                assertThat( carDto.isManual() ).isFalse();
            } );
    }
}
