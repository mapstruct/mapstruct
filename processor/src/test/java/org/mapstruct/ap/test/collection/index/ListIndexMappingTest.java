/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.index;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oliver Erhart
 */
@WithClasses({
    Car.class,
    CarDto.class,
    CarWithArrayList.class,
    CarWithDriverNameDto.class,
    Person.class,
    PersonDto.class,
    ListIndexMapper.class,
    ListIndexNestedPropertyMapper.class
})
@IssueKey("1321")
public class ListIndexMappingTest {

    @ProcessorTest
    public void shouldMapWithDirectAssignment() {

        Car source = new Car();
        List<Person> personList = new ArrayList<>();
        personList.add( new Person( "First" ) );
        personList.add( new Person( "Second" ) );
        source.setPersonList( personList );

        CarDto target = Mappers.getMapper( ListIndexMapper.class ).mapList( source );

        assertThat( target.getDriver().getName() ).isEqualTo( "First" );
    }

    @ProcessorTest
    public void shouldMapWithDirectArrayListAssignment() {

        CarWithArrayList source = new CarWithArrayList();
        ArrayList<Person> personList = new ArrayList<>();
        personList.add( new Person( "First" ) );
        personList.add( new Person( "Second" ) );
        source.setPersonList( personList );

        CarDto target = Mappers.getMapper( ListIndexMapper.class ).mapArrayList( source );

        assertThat( target.getDriver().getName() ).isEqualTo( "First" );
    }

    @ProcessorTest
    public void shouldBeNullWhenDirectAssignmentIndexOutOfBounds() {

        Car source = new Car();
        source.setPersonList( new ArrayList<>() );

        CarDto target = Mappers.getMapper( ListIndexMapper.class ).mapList( source );

        assertThat( target.getDriver() ).isNull();
    }

    @ProcessorTest
    public void shouldMapWithNestedPropertyAssignment() {

        Car source = new Car();
        List<Person> personList = new ArrayList<>();
        personList.add( new Person( "First" ) );
        personList.add( new Person( "Second" ) );
        source.setPersonList( personList );

        CarWithDriverNameDto target = Mappers.getMapper( ListIndexNestedPropertyMapper.class ).sourceToTarget( source );

        assertThat( target.getDriver().getName() ).isEqualTo( "First" );
        assertThat( target.getDriverName() ).isEqualTo( "First" );
    }

    @ProcessorTest
    public void shouldBeNullWhenNestedPropertyAssignmentIndexOutOfBounds() {

        Car source = new Car();
        source.setPersonList( new ArrayList<>() );

        CarWithDriverNameDto target = Mappers.getMapper( ListIndexNestedPropertyMapper.class ).sourceToTarget( source );

        assertThat( target.getDriver() ).isNull();
        assertThat( target.getDriverName() ).isNull();
    }

    @ProcessorTest
    @WithClasses(ErroneousListIndexOnStringMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousListIndexOnStringMapper.class,
                line = 14,
                message = "Can't access element with index because \"name\" is not of type List."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousListIndexOnStringMapper.class,
                line = 14,
                message = "No property named \"name[0]\" exists in source parameter(s). Did you mean \"name\"?"
            )
        }
    )
    public void errorWhenSourceIsStringInsteadOfList() { }

    @ProcessorTest
    @WithClasses(ErroneousListIndexIsNoNumberMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousListIndexIsNoNumberMapper.class,
                message = "Index accessor must be an integer but was: \"x\"."
            )
        }
    )
    public void errorWhenIndexIsNoNumber() { }

    @ProcessorTest
    @WithClasses({
        ErroneousListIndexOnSetMapper.class,
        DriverList.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousListIndexOnSetMapper.class,
                line = 14,
                message = "Can't access element with index because \"drivers\" is not of type List."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousListIndexOnSetMapper.class,
                line = 14,
                message = "No property named \"drivers[0].name\" exists in source parameter(s)." +
                    " Did you mean \"drivers\"?"
            )
        }
    )
    public void errorWhenSourceIsSetInsteadOfList() { }

}
