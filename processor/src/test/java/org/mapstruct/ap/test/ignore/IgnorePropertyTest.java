/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for ignoring properties during the mapping.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Animal.class, AnimalDto.class, AnimalMapper.class })
public class IgnorePropertyTest {

    @ProcessorTest
    @IssueKey("72")
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
    @IssueKey("1392")
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
    @IssueKey("337")
    public void propertyIsIgnoredInReverseMappingWhenSourceIsAlsoSpecifiedICWIgnore() {
        AnimalDto animalDto = new AnimalDto( "Bruno", 100, 23, "black" );

        Animal animal = AnimalMapper.INSTANCE.animalDtoToAnimal( animalDto );

        assertThat( animal ).isNotNull();
        assertThat( animalDto.getName() ).isEqualTo( "Bruno" );
        assertThat( animalDto.getSize() ).isEqualTo( 100 );
        assertThat( animal.getColour() ).isNull();
        assertThat( animal.publicColour ).isNull();
    }

    @ProcessorTest
    @IssueKey("833")
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
}
