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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for ignoring properties during the mapping.
 *
 * @author Ivashin Aleksey
 */
@WithClasses({ Animal.class, AnimalDto.class, Zoo.class, ZooDto.class, ZooMapper.class})
public class IgnoredPropertyTest {

    @ProcessorTest
    @IssueKey("1958")
    @WithClasses( { AnimalMapper.class } )
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
    @WithClasses( { ErroneousMapper.class } )
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(type = ErroneousMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 20,
                            message = "Target property \"name\" must not be mapped more than once." ),
                    @Diagnostic(type = ErroneousMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 24,
                            message = "Target property \"publicColor\" must not be mapped more than once." )
            }
    )
    public void shouldFailToGenerateMappings() {
    }

    @ProcessorTest
    @IssueKey("1958")
    @WithClasses( { AnimalMapper.class } )
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
    @WithClasses( { AnimalMapper.class } )
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
