/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import static org.assertj.core.api.Assertions.assertThat;

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
    SoccerTeamTarget.class
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
}
