/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import org.mapstruct.ap.test.ignore.AnimalMapper;
import org.mapstruct.ap.test.ignore.Animal;
import org.mapstruct.ap.test.ignore.AnimalDto;

/**
 * Test for ignoring properties during the mapping.
 *
 * @author Gunnar Morling
 */
public class AnimalTest {

    @Test
    public void shouldNotPropagateIgnoredPropertyGivenViaTargetAttribute() {
        Animal animal = new Animal( "Bruno", 100, 23, "black" );

        AnimalDto animalDto = AnimalMapper.INSTANCE.animalToDto( animal );

        assertThat( animalDto ).isNotNull();
        assertThat( animalDto.getName() ).isEqualTo( "Bruno" );
        assertThat( animalDto.getSize() ).isEqualTo( 100 );
        assertThat( animalDto.getAge() ).isNull();
        assertThat( animalDto.getColor() ).isNull();
    }

    @Test
    public void shouldNotPropagateIgnoredPropertyInReverseMappingWhenSourceAndTargetAreSpecified() {
        AnimalDto animalDto = new AnimalDto( "Bruno", 100, 23, "black" );

        Animal animal = AnimalMapper.INSTANCE.animalDtoToAnimal( animalDto );

        assertThat( animal ).isNotNull();
        assertThat( animalDto.getName() ).isEqualTo( "Bruno" );
        assertThat( animalDto.getSize() ).isEqualTo( 100 );
        assertThat( animal.getColour() ).isNull();
    }
}
