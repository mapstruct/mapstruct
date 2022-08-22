/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2825;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author orange add
 */

@IssueKey("2825")
public class Issue2825Test {

    @ProcessorTest
    @WithClasses({Animal.class, Dog.class, Cat.class, TargetAnimal.class, AnimalMapper.class})
    public void generalMethodShouldSuccessWork() {
        Animal animal = new Dog();
        animal.setName( "dog" );
        AnimalMapper instance = AnimalMapper.INSTANCE;
        TargetAnimal targetAnimal = instance.map( animal );
        assertThat( targetAnimal.getName() ).isNotBlank();
    }

    @ProcessorTest
    @WithClasses({Animal.class, Dog.class, Cat.class, TargetAnimal.class, CustomerAnimalMapper.class})
    public void customerMethodShouldSuccessWork() {
        Animal animal = new Dog();
        animal.setName( "dog" );
        CustomerAnimalMapper instance = CustomerAnimalMapper.INSTANCE;
        TargetAnimal targetAnimal = instance.map( animal );
        assertThat( targetAnimal.getName() ).isNotBlank();
    }

    @ProcessorTest
    @WithClasses({Apple.class, AppleDto.class, Fruit.class, FruitDto.class, FruitMapper.class, Orange.class,
            OrangeDto.class})
    public void generalTargetImpl() {
        Orange orange = new Orange();
        orange.setName( "Orange" );
        FruitMapper instance = FruitMapper.INSTANCE;
        FruitDto fruitDto = instance.map( orange );
        assertThat( fruitDto.getName() ).isNotBlank();
    }

}
