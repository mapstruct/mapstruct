/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.abstractsuperclass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

@IssueKey("366")
@WithClasses({
    AbstractVehicle.class,
    VehicleCollection.class,
    Bike.class,
    BikeDto.class,
    Car.class,
    CarDto.class,
    VehicleCollectionDto.class,
    VehicleDto.class,
})
public class AbstractSuperClassTest {

    @ProcessorTest
    @WithClasses( SubclassWithAbstractSuperClassMapper.class )
    void downcastMappingInCollection() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Bike() );

        VehicleCollectionDto result = SubclassWithAbstractSuperClassMapper.INSTANCE.map( vehicles );

        assertThat( result.getVehicles() ).doesNotContainNull();
        assertThat( result.getVehicles() ) // remove generic so that test works.
            .extracting( vehicle -> (Class) vehicle.getClass() )
            .containsExactly( CarDto.class, BikeDto.class );
    }

    @ProcessorTest
    @WithClasses( SubclassWithAbstractSuperClassMapper.class )
    void mappingOfUnknownChildThrowsIllegalArgumentException() {
        VehicleCollection vehicles = new VehicleCollection();
        vehicles.getVehicles().add( new Car() );
        vehicles.getVehicles().add( new Motorcycle() );

        assertThatThrownBy( () -> SubclassWithAbstractSuperClassMapper.INSTANCE.map( vehicles ) )
            .isInstanceOf( IllegalArgumentException.class )
            .hasMessage( "Not all subclasses are supported for this mapping. "
                + "Missing for class org.mapstruct.ap.test.subclassmapping.abstractsuperclass.Motorcycle" );
    }

    @WithClasses( ErroneousSubclassWithAbstractSuperClassMapper.class )
    @ProcessorTest
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED, diagnostics = {
        @Diagnostic(type = ErroneousSubclassWithAbstractSuperClassMapper.class,
            kind = javax.tools.Diagnostic.Kind.ERROR,
            line = 26,
            message = "The return type VehicleDto is an abstract class or interface. "
                + "Provide a non abstract / non interface result type or a factory method.")
    })
    void compileErrorWithAbstractClass() {
    }
}
