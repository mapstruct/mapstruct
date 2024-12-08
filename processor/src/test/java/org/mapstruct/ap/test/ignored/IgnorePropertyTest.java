/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignored;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import javax.tools.Diagnostic.Kind;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for ignoring properties during the mapping.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Animal.class, AnimalDto.class, AnimalMapper.class, Zoo.class, ZooDto.class, ZooMapper.class})
public class IgnorePropertyTest {

    @ProcessorTest
    @IssueKey("1958")
    public void shouldNotPropagateIgnoredPropertyGivenViaTargetAttribute() {
        Animal animal = new Animal( "Bruno", 100, 23, "black" );

        AnimalDto animalDto = AnimalMapper.INSTANCE.animalToDto( animal );

        assertThat( animalDto ).isNotNull();
        assertThat( animalDto.getName() ).isEqualTo( "Bruno" );
        assertThat( animalDto.getSize() ).isEqualTo( 100 );
        assertThat( animalDto.getAge() ).isNull();
        assertThat( animalDto.publicAge ).isNull();
        assertThat( animalDto.getColor() ).isNull();
        assertThat( animalDto.publicColor ).isNull();
    }

    @ProcessorTest
    @IssueKey("1958")
    public void shouldIgnoreAllTargetPropertiesWithNoUnmappedTargetWarnings() {
        Animal animal = new Animal( "Bruno", 100, 23, "black" );

        AnimalDto animalDto = AnimalMapper.INSTANCE.animalToDtoIgnoreAll( animal );

        assertThat( animalDto ).isNotNull();
        assertThat( animalDto.getName() ).isNull();
        assertThat( animalDto.getSize() ).isNull();
        assertThat( animalDto.getAge() ).isNull();
        assertThat( animalDto.publicAge ).isNull();
        assertThat( animalDto.getColor() ).isNull();
        assertThat( animalDto.publicColor ).isNull();
    }

    @ProcessorTest
    @IssueKey("1958")
    @WithClasses({Preditor.class, PreditorDto.class, ErroneousTargetHasNoWriteAccessorMapper.class})
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousTargetHasNoWriteAccessorMapper.class,
                kind = Kind.ERROR,
                line = 22,
                message = "Property \"hasClaws\" has no write accessor in PreditorDto.")
        }
    )
    public void shouldGiveErrorOnMappingForReadOnlyProp() {
    }

    @ProcessorTest
    @IssueKey("1958")
    public void shouldNotPropagateIgnoredInnerPropertyGivenViaTargetAttribute() {
        Animal animal = new Animal( "Bruno", 100, 23, "black" );
        Zoo zoo = new Zoo(animal, "Test name", "test address");

        ZooDto zooDto = ZooMapper.INSTANCE.zooToDto( zoo );

        assertThat( zooDto ).isNotNull();
        assertThat( zooDto.getName() ).isEqualTo( "Test name" );
        assertThat( zooDto.getAddress() ).isEqualTo( "test address" );
        assertThat( zooDto.getAnimal() ).isNotNull();
        assertThat( zooDto.getAnimal().getName() ).isEqualTo( "Bruno" );
        assertThat( zooDto.getAnimal().getAge() ).isNull();
        assertThat( zooDto.getAnimal().publicAge ).isNull();
        assertThat( zooDto.getAnimal().getColor() ).isNull();
        assertThat( zooDto.getAnimal().publicColor ).isNull();
        assertThat( zooDto.getAnimal().getSize() ).isNull();
    }

    @ProcessorTest
    @IssueKey("1958")
    public void shouldNotPropagateIgnoredInnerPropertyGivenViaTargetAttribute2() {
        Animal animal = new Animal( "Bruno", 100, 23, "black" );
        Zoo zoo = new Zoo(animal, "Test name", "test address");

        ZooDto zooDto = ZooMapper.INSTANCE.zooToDto2( zoo );

        assertThat( zooDto ).isNotNull();
        assertThat( zooDto.getName() ).isEqualTo( "Test name" );
        assertThat( zooDto.getAddress() ).isNull();
        assertThat( zooDto.getAnimal() ).isNotNull();
        assertThat( zooDto.getAnimal().getName() ).isEqualTo( "Bruno" );
        assertThat( zooDto.getAnimal().getAge() ).isNull();
        assertThat( zooDto.getAnimal().publicAge ).isNull();
        assertThat( zooDto.getAnimal().getColor() ).isNull();
        assertThat( zooDto.getAnimal().publicColor ).isNull();
        assertThat( zooDto.getAnimal().getSize() ).isNull();
    }
}
