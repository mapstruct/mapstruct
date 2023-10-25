/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletolist;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Xiu-Hong Kooi
 */
@WithClasses({ FruitsMenu.class, FruitSalad.class, Fruit.class, FruitsMapper.class })
public class IterableToListMappingTest {

    @ProcessorTest
    @IssueKey("3376")
    public void shouldMapIterableToList() {
        List<Fruit> fruits =  Arrays.asList( new Fruit( "mango" ), new Fruit( "apple" ),
                new Fruit( "banana" ) );
        FruitsMenu menu = new FruitsMenu(fruits);
        FruitSalad salad = FruitsMapper.INSTANCE.fruitsMenuToSalad( menu );
        Iterator<Fruit> itr = salad.getFruits().iterator();
        assertThat( itr.next().getType() ).isEqualTo( "mango" );
        assertThat( itr.next().getType() ).isEqualTo( "apple" );
        assertThat( itr.next().getType() ).isEqualTo( "banana" );
    }

    @ProcessorTest
    @IssueKey("3376")
    public void shouldMapListToIterable() {
        List<Fruit> fruits =  Arrays.asList( new Fruit( "mango" ), new Fruit( "apple" ),
                new Fruit( "banana" ) );
        FruitSalad salad = new FruitSalad(fruits);
        FruitsMenu menu = FruitsMapper.INSTANCE.fruitSaladToMenu( salad );
        assertThat( menu.getFruits() ).extracting( Fruit::getType ).containsExactly( "mango", "apple", "banana" );
    }
}
