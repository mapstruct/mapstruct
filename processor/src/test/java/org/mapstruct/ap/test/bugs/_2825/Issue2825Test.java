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
@WithClasses({
    Animal.class,
    Cat.class,
    Dog.class,
    Issue2825Mapper.class,
    TargetAnimal.class,
})
public class Issue2825Test {

    @ProcessorTest
    public void mappingMethodShouldNotBeReusedForSubclassMappings() {
        Dog dog = new Dog();
        dog.setName( "Lucky" );
        dog.setRace( "Shepherd" );
        TargetAnimal target = Issue2825Mapper.INSTANCE.map( dog );
        assertThat( target.getName() ).isEqualTo( "Lucky" );
        assertThat( target.getRace() ).isEqualTo( "Shepherd" );
    }

}
