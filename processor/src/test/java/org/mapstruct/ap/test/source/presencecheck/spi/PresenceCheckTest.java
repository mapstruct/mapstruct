/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for correct handling of source presence checks.
 *
 * @author Sean Huang
 */
@WithClasses({
    SourceTargetMapper.class,
    Source.class,
    Target.class,
    SoccerTeamMapper.class,
    SoccerTeamSource.class,
    GoalKeeper.class,
    SoccerTeamTarget.class,
    TargetWithPresenceTracking.class,
    SourceTargetWithPresenceTrackingMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class PresenceCheckTest {

    @Test
    public void testWithSourcesPresent() {

        Source source = new Source();

        source.setSomePrimitiveDouble( 5.0 );
        source.setSomeInteger( 7 );
        source.setSomeList( Arrays.asList( "first", "second" ) );
        source.setSomeArray( new String[]{ "x", "y" } );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomePrimitiveDouble() ).isEqualTo( 5.0 );
        assertThat( target.getSomeInteger() ).isEqualTo( 7 );
        assertThat( target.getSomeList() ).containsExactly( "first", "second" );
        assertThat( target.getSomeArray() ).isEqualTo( new String[]{ "x", "y"} );
    }

    @Test
    public void testWithSourcesAbsent() {

        Source source = new Source();

        source.setHasSomePrimitiveDouble( false );
        source.setHasSomeInteger( false );
        source.setHasSomeList( false );
        source.setHasSomeArray( false );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomePrimitiveDouble() ).isEqualTo( 0d );
        assertThat( target.getSomeInteger() ).isNull();
        assertThat( target.getSomeList() ).isNull();
        assertThat( target.getSomeArray() ).isNull();
   }

    @Test
    public void testUpdateMethodWithSourcesPresent() {

        Source source = new Source();

        source.setSomePrimitiveDouble( 5.0 );
        source.setSomeInteger( 7 );
        source.setSomeList( Arrays.asList( "first", "second" ) );
        source.setSomeArray( new String[]{ "x", "y" } );

        Target target = new Target();
        SourceTargetMapper.INSTANCE.sourceToTarget( source, target );

        assertThat( target.getSomePrimitiveDouble() ).isEqualTo( 5.0 );
        assertThat( target.getSomeInteger() ).isEqualTo( 7 );
        assertThat( target.getSomeList() ).containsExactly( "first", "second" );
        assertThat( target.getSomeArray() ).isEqualTo( new String[]{ "x", "y"} );
    }

    @Test
    public void testUpdateMethodWithSourcesAbsent() {

        Source source = new Source();

        source.setHasSomePrimitiveDouble( false );
        source.setHasSomeInteger( false );
        source.setHasSomeList( false );
        source.setHasSomeArray( false );

        Target target = new Target();
        SourceTargetMapper.INSTANCE.sourceToTarget( source, target );

        assertThat( target.getSomePrimitiveDouble() ).isEqualTo( 0d );
        assertThat( target.getSomeInteger() ).isNull();
        assertThat( target.getSomeList() ).isNull();
        assertThat( target.getSomeArray() ).isNull();
    }

   @Test
    public void testWithSourcesPresentAndDefault() {

        Source source = new Source();

        source.setSomePrimitiveDouble( 5.0 );
        source.setSomeInteger( 7 );
        source.setSomeList( Arrays.asList( "first", "second" ) );
        source.setSomeArray( new String[]{ "x", "y" } );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetWitDefaults( source );

        assertThat( target.getSomePrimitiveDouble() ).isEqualTo( 5.0 );
        assertThat( target.getSomeInteger() ).isEqualTo( 7 );
        assertThat( target.getSomeList() ).containsExactly( "first", "second" );
        assertThat( target.getSomeArray() ).isEqualTo( new String[]{ "x", "y"} );
   }

    @Test
    public void testWithSourcesAbsentAndDefault() {

        Source source = new Source();

        source.setHasSomePrimitiveDouble( false );
        source.setHasSomeInteger( false );
        source.setHasSomeList( false );
        source.setHasSomeArray( false );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetWitDefaults( source );

        assertThat( target.getSomePrimitiveDouble() ).isEqualTo( 111.1d );
        assertThat( target.getSomeInteger() ).isEqualTo( 222 );
        assertThat( target.getSomeList() ).containsExactly( "a", "b" );
        assertThat( target.getSomeArray() ).isEqualTo( new String[]{ "u", "v"} );
    }

    @Test
    public void testAdderWithSourcesPresent() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        soccerTeamSource.setPlayers( Arrays.asList( "pele", "cruyf" ) );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapAdder( soccerTeamSource );

        assertThat( target.getPlayers() ).containsExactly( "pele", "cruyf" );
    }

    @Test
    public void testAdderWithSourcesAbsent() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        soccerTeamSource.setHasPlayers( false );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapAdder( soccerTeamSource );

        assertThat( target.getPlayers() ).isNull();
    }

    @Test
    public void testNestedWithSourcesPresent() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        GoalKeeper goalKeeper = new GoalKeeper();
        goalKeeper.setName( "Buffon" );
        soccerTeamSource.setGoalKeeper( goalKeeper );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapNested( soccerTeamSource );

        assertThat( target.getGoalKeeperName() ).isEqualTo( "Buffon" );
    }

    @Test
    public void testNestedWithSourcesAbsentOnRootLevel() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        soccerTeamSource.setHasGoalKeeper( false );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapNested( soccerTeamSource );

        assertThat( target.getGoalKeeperName() ).isNull();
    }

    @Test
    public void testNestedWithSourcesAbsentOnNestingLevel() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        GoalKeeper goalKeeper = new GoalKeeper();
        goalKeeper.setHasName( false );
        soccerTeamSource.setGoalKeeper( goalKeeper );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapNested( soccerTeamSource );

        assertThat( target.getGoalKeeperName() ).isNull();
    }

    @Test
    public void testPresenceWithSourcesAbsent() {

        Source source = new Source();

        source.setHasSomePrimitiveDouble( false );
        source.setHasSomeInteger( false );
        source.setHasSomeList( false );
        source.setHasSomeArray( false );

        TargetWithPresenceTracking target = SourceTargetWithPresenceTrackingMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomePrimitiveDouble() ).isEqualTo( 0d );
        assertThat( target.getSomeInteger() ).isNull();
        assertThat( target.getSomeList() ).isNull();
        assertThat( target.getSomeArray() ).isNull();

        assertFalse( target.hasSomePrimitiveDouble() );
        assertFalse( target.hasSomeInteger() );
        assertFalse( target.hasSomeList() );
        assertFalse( target.hasSomeArray() );
    }

    @Test
    public void testPresenceWithSourcePresent() {
        Source source = new Source();

        source.setSomePrimitiveDouble( 5.0 );
        source.setSomeInteger( 7 );
        source.setSomeList( Arrays.asList( "first", "second" ) );
        source.setSomeArray( new String[]{ "x", "y" } );

        TargetWithPresenceTracking target = SourceTargetWithPresenceTrackingMapper.INSTANCE.sourceToTarget( source );

        assertTrue( target.hasSomePrimitiveDouble() );
        assertTrue( target.hasSomeInteger() );
        assertTrue( target.hasSomeList() );
        assertTrue( target.hasSomeArray() );

    }
}
