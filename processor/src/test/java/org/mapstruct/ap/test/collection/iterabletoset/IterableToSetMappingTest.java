/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletoset;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Xiu-Hong Kooi
 */
@WithClasses({ FruitsMenu.class, FruitSalad.class, Fruit.class, FruitsMapper.class })
public class IterableToSetMappingTest {

    @ProcessorTest
    @IssueKey("3376")
    public void shouldMapIterableToSet() {
        Set<Fruit> fruits =  new HashSet<>( Arrays.asList( new Fruit( "mango" ), new Fruit( "apple" ),
                new Fruit( "banana" ) ) );
        FruitsMenu menu = new FruitsMenu(fruits);
        FruitSalad salad = FruitsMapper.INSTANCE.fruitsMenuToSalad( menu );
        Iterator<Fruit> itr = salad.getFruits().iterator();
        Set<String> fruitTypes = fruits.stream().map( Fruit::getType ).collect( Collectors.toSet() );
        assertThat( fruitTypes.contains( itr.next().getType() ) );
        assertThat( fruitTypes.contains( itr.next().getType() ) );
        assertThat( fruitTypes.contains( itr.next().getType() ) );
    }

    @ProcessorTest
    @IssueKey("3376")
    public void shouldMapSetToIterable() {
        Set<Fruit> fruits =  new HashSet<>( Arrays.asList( new Fruit( "mango" ), new Fruit( "apple" ),
                new Fruit( "banana" ) ) );
        FruitSalad salad = new FruitSalad(fruits);
        FruitsMenu menu = FruitsMapper.INSTANCE.fruitSaladToMenu( salad );
        assertThat( menu.getFruits() ).extracting( Fruit::getType ).contains( "mango", "apple", "banana" );
    }
}
