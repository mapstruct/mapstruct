/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

/**
 * Test for correct handling of source presence checks for nested objects.
 *
 * @author Cindy Wang
 */
@WithClasses({
    SoccerTeamMapperNestedObjects.class,
    SoccerTeamSource.class,
    GoalKeeper.class,
    SoccerTeamTargetWithPresenceCheck.class,
    Referee.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class PresenceCheckNestedObjectsTest {

    @Test
    public void testNestedWithSourcesAbsentOnNestingLevel() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        GoalKeeper goalKeeper = new GoalKeeper();
        goalKeeper.setHasName( false );
        goalKeeper.setName( "shouldNotBeUsed" );
        soccerTeamSource.setGoalKeeper( goalKeeper );
        soccerTeamSource.setHasRefereeName( false );
        soccerTeamSource.setRefereeName( "shouldNotBeUsed" );

        SoccerTeamTargetWithPresenceCheck target = SoccerTeamMapperNestedObjects.INSTANCE.mapNested( soccerTeamSource );

        assertThat( target.getGoalKeeperName() ).isNull();
        assertFalse( target.hasGoalKeeperName() );
        assertThat( target.getReferee() ).isNotNull();
        assertThat( target.getReferee().getName() ).isNull();

    }
}
