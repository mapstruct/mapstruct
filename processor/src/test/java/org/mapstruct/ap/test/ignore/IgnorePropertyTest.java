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
package org.mapstruct.ap.test.ignore;

import static org.assertj.core.api.Assertions.assertThat;

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
 * Test for ignoring properties during the mapping.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Animal.class, AnimalDto.class, AnimalMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class IgnorePropertyTest {

    @Test
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

    @Test
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

    @Test
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

    @Test
    @IssueKey("833")
    @WithClasses({Preditor.class, PreditorDto.class, ErroneousTargetHasNoWriteAccessorMapper.class})
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousTargetHasNoWriteAccessorMapper.class,
                kind = Kind.ERROR,
                line = 35,
                messageRegExp = "Property \"hasClaws\" has no write accessor in " +
                    "org.mapstruct.ap.test.ignore.PreditorDto\\.")
        }
    )
    public void shouldGiveErrorOnMappingForReadOnlyProp() {
    }
}
