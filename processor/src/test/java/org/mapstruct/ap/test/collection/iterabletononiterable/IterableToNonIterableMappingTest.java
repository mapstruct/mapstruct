/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletononiterable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@WithClasses({ Source.class, Target.class, SourceTargetMapper.class, StringListMapper.class, FruitsMenu.class,
        FruitSalad.class, Fruit.class, FruitsMapper.class })
public class IterableToNonIterableMappingTest {

    @ProcessorTest
    @IssueKey("6")
    public void shouldMapStringListToStringUsingCustomMapper() {
        Source source = new Source();
        source.setNames( Arrays.asList( "Alice", "Bob", "Jim" ) );
        source.publicNames = Arrays.asList( "Alice", "Bob", "Jim" );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNames() ).isEqualTo( "Alice-Bob-Jim" );
        assertThat( target.publicNames ).isEqualTo( "Alice-Bob-Jim" );
    }

    @ProcessorTest
    @IssueKey("6")
    public void shouldReverseMapStringListToStringUsingCustomMapper() {
        Target target = new Target();
        target.setNames( "Alice-Bob-Jim" );
        target.publicNames = "Alice-Bob-Jim";

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getNames() ).isEqualTo( Arrays.asList( "Alice", "Bob", "Jim" ) );
        assertThat( source.publicNames ).isEqualTo( Arrays.asList( "Alice", "Bob", "Jim" ) );
    }

    @ProcessorTest
    @IssueKey("607")
    public void shouldMapIterableToNonIterable() {
        List<Fruit> fruits =  Arrays.asList( new Fruit( "mango" ), new Fruit( "apple" ),
                new Fruit( "banana" ) );
        FruitsMenu menu = new FruitsMenu(fruits);
        FruitSalad salad = FruitsMapper.INSTANCE.fruitsMenuToSalad( menu );
        assertThat( salad.getFruits().get( 0 ).getType() ).isEqualTo( "mango" );
        assertThat( salad.getFruits().get( 1 ).getType() ).isEqualTo( "apple" );
        assertThat( salad.getFruits().get( 2 ).getType() ).isEqualTo( "banana" );
    }

    @ProcessorTest
    @IssueKey("607")
    public void shouldMapNonIterableToIterable() {
        List<Fruit> fruits =  Arrays.asList( new Fruit( "mango" ), new Fruit( "apple" ),
                new Fruit( "banana" ) );
        FruitSalad salad = new FruitSalad(fruits);
        FruitsMenu menu = FruitsMapper.INSTANCE.fruitSaladToMenu( salad );
        assertThat( salad.getFruits() ).extracting( Fruit::getType ).containsExactly( "mango", "apple", "banana" );
    }
}
