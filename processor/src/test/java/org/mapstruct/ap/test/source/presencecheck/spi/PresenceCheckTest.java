/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

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
    SoccerTeamTarget.class
})
public class PresenceCheckTest {

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
    public void testAdderWithSourcesPresent() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        soccerTeamSource.setPlayers( Arrays.asList( "pele", "cruyf" ) );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapAdder( soccerTeamSource );

        assertThat( target.getPlayers() ).containsExactly( "pele", "cruyf" );
    }

    @ProcessorTest
    public void testAdderWithSourcesAbsent() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        soccerTeamSource.setHasPlayers( false );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapAdder( soccerTeamSource );

        assertThat( target.getPlayers() ).isNull();
    }

    @ProcessorTest
    public void testNestedWithSourcesPresent() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        GoalKeeper goalKeeper = new GoalKeeper();
        goalKeeper.setName( "Buffon" );
        soccerTeamSource.setGoalKeeper( goalKeeper );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapNested( soccerTeamSource );

        assertThat( target.getGoalKeeperName() ).isEqualTo( "Buffon" );
    }

    @ProcessorTest
    public void testNestedWithSourcesAbsentOnRootLevel() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        soccerTeamSource.setHasGoalKeeper( false );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapNested( soccerTeamSource );

        assertThat( target.getGoalKeeperName() ).isNull();
    }

    @ProcessorTest
    public void testNestedWithSourcesAbsentOnNestingLevel() {

        SoccerTeamSource soccerTeamSource = new SoccerTeamSource();
        GoalKeeper goalKeeper = new GoalKeeper();
        goalKeeper.setHasName( false );
        soccerTeamSource.setGoalKeeper( goalKeeper );

        SoccerTeamTarget target = SoccerTeamMapper.INSTANCE.mapNested( soccerTeamSource );

        assertThat( target.getGoalKeeperName() ).isNull();
    }
}
