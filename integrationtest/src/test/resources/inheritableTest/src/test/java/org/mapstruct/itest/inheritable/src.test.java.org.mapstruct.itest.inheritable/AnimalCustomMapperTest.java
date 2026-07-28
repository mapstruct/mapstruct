package org.mapstruct.itest.inheritable

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

class AnimalCustomMapperTest {
    @Test
    void testCustomMapperIsGenereated() {
        Animal animal = new Animal("Bruno", 100, 23, "black");

        AnimalDto animalDto = AnimalCustomMapper.INSTANCE.animalToDtoCustom(animal);

        assertThat(animalDto).isNotNull();
        assertThat(animalDto.getName()).isEqualTo("Bruno");
        assertThat(animalDto.getSize()).isEqualTo(100);
        assertThat(animalDto.getAge()).isNull();
        assertThat(animalDto.getColor()).isNull();
    }
}