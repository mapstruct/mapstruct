/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class PresenceCheckMappingTest {

    @Test
    @WithClasses({
        CollectionPresenceMapper.class
    })
    public void collectionPresenceCheckShouldBeUsedByDefault() {
        CollectionPresenceMapper.Source source = new CollectionPresenceMapper.Source( Collections.emptyList() );
        CollectionPresenceMapper.Target target = CollectionPresenceMapper.INSTANCE.map( source );

        assertThat( target.getPlayers() ).isNull();
    }

    @Test
    @WithClasses({
        CollectionWithNullValueCheckMapper.class
    })
    public void collectionPresenceCheckShouldBeUsedByWhenNullValueCheckIsAlways() {
        CollectionWithNullValueCheckMapper.Source source =
            new CollectionWithNullValueCheckMapper.Source( Collections.emptyList() );
        CollectionWithNullValueCheckMapper.Target target =
            CollectionWithNullValueCheckMapper.INSTANCE.map( source );

        assertThat( target.getPlayers() ).isNull();
    }

    @Test
    @WithClasses({
        CollectionWithNonDirectMapper.class
    })
    public void nonDirectCollectionPresenceCheckShouldBeUsedByDefault() {
        CollectionWithNonDirectMapper.Source source =
            new CollectionWithNonDirectMapper.Source( Collections.emptyList() );
        CollectionWithNonDirectMapper.Target target = CollectionWithNonDirectMapper.INSTANCE.map( source );

        assertThat( target.getPlayers() ).isNull();
    }

    @Test
    @WithClasses({
        NonDirectMapper.class
    })
    public void nonDirectMappingWithPresenceCheckShouldBeUsedByDefault() {
        NonDirectMapper.SoccerTeamSource source =
            new NonDirectMapper.SoccerTeamSource( new NonDirectMapper.GoalKeeperSource( "" ) );
        NonDirectMapper.SoccerTeam target = NonDirectMapper.INSTANCE.map( source );

        assertThat( target.getGoalKeeper() ).isNull();
    }
}
