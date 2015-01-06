/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.collection;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@WithClasses({ Source.class, Target.class, Colour.class, SourceTargetMapper.class, TestList.class, TestMap.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class CollectionMappingTest {

    @Test
    @IssueKey("6")
    public void shouldMapNullList() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringList() ).isNull();
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapNullList() {
        Target target = new Target();

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringList() ).isNull();
    }

    @Test
    @IssueKey("6")
    public void shouldMapList() {
        Source source = new Source();
        source.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("92")
    public void shouldMapListWithoutSetter() {
        Source source = new Source();
        source.setStringList2( Arrays.asList( "Bob", "Alice" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringListNoSetter() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapList() {
        Target target = new Target();
        target.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapListAsCopy() {
        Source source = new Source();
        source.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        target.getStringList().add( "Bill" );

        assertThat( source.getStringList() ).containsExactly( "Bob", "Alice" );
        assertThat( source.getStringList() ).isNotEqualTo( target.getStringList() );
    }

    @Test
    @IssueKey( "153" )
    public void shouldMapListWithClearAndAddAll() {
        Source source = new Source();
        source.setOtherStringList( Arrays.asList( "Bob", "Alice" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        target.getOtherStringList().add( "Bill" );

        assertThat( source.getOtherStringList() ).containsExactly( "Bob", "Alice" );

        // prepare a test list to monitor add all behaviour
        List<String> testList = new TestList<String>();
        testList.addAll( target.getOtherStringList() );
        TestList.setAddAllCalled( false );
        target.setOtherStringList( testList );

        // prepare new source
        source.setOtherStringList( Arrays.asList( "Bob" ) );
        List<String> originalInstance = target.getOtherStringList();

        SourceTargetMapper.INSTANCE.sourceToTargetTwoArg( source, target );

        assertThat( target.getOtherStringList() ).isSameAs( originalInstance );
        assertThat( target.getOtherStringList() ).containsExactly( "Bob" );
        assertThat( TestList.isAddAllCalled() ).isTrue();
        TestList.setAddAllCalled( false );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapListAsCopy() {
        Target target = new Target();
        target.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        source.getStringList().add( "Bill" );

        assertThat( target.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapArrayList() {
        Source source = new Source();
        source.setStringArrayList( new ArrayList<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringArrayList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapArrayList() {
        Target target = new Target();
        target.setStringArrayList( new ArrayList<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringArrayList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapSet() {
        Source source = new Source();
        source.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringSet() ).contains( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapSet() {
        Target target = new Target();
        target.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringSet() ).contains( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapSetAsCopy() {
        Source source = new Source();
        source.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        target.getStringSet().add( "Bill" );

        assertThat( source.getStringSet() ).containsOnly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapHashSetAsCopy() {
        Source source = new Source();
        source.setStringHashSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        target.getStringHashSet().add( "Bill" );

        assertThat( source.getStringHashSet() ).containsOnly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapSetAsCopy() {
        Target target = new Target();
        target.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        source.getStringSet().add( "Bill" );

        assertThat( target.getStringSet() ).containsOnly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapListToCollection() {
        Source source = new Source();
        source.setIntegerList( Arrays.asList( 1, 2 ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getIntegerCollection() ).containsOnly( 1, 2 );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapListToCollection() {
        Target target = new Target();
        target.setIntegerCollection( Arrays.asList( 1, 2 ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getIntegerList() ).containsOnly( 1, 2 );
    }

    @Test
    @IssueKey("6")
    public void shouldMapIntegerSetToRawSet() {
        Source source = new Source();
        source.setIntegerSet( new HashSet<Integer>( Arrays.asList( 1, 2 ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSet() ).containsOnly( 1, 2 );
    }

    @Test
    @IssueKey("6")
    public void shouldMapIntegerSetToStringSet() {
        Source source = new Source();
        source.setAnotherIntegerSet( new HashSet<Integer>( Arrays.asList( 1, 2 ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getAnotherStringSet() ).containsOnly( "1", "2" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapIntegerSetToStringSet() {
        Target target = new Target();
        target.setAnotherStringSet( new HashSet<String>( Arrays.asList( "1", "2" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getAnotherIntegerSet() ).containsOnly( 1, 2 );
    }

    @Test
    @IssueKey("6")
    public void shouldMapSetOfEnumToStringSet() {
        Source source = new Source();
        source.setColours( EnumSet.of( Colour.BLUE, Colour.GREEN ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getColours() ).containsOnly( "BLUE", "GREEN" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapSetOfEnumToStringSet() {
        Target target = new Target();
        target.setColours( new HashSet<String>( Arrays.asList( "BLUE", "GREEN" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getColours() ).containsOnly( Colour.GREEN, Colour.BLUE );
    }

    @Test
    public void shouldMapMapAsCopy() {
        Source source = new Source();

        Map<String, Long> map = new HashMap<String, Long>();
        map.put( "Bob", 123L );
        map.put( "Alice", 456L );
        source.setStringLongMap( map );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        target.getStringLongMap().put( "Bill", 789L );

        assertThat( source.getStringLongMap() ).hasSize( 2 );
        assertThat( target.getStringLongMap() ).hasSize( 3 );
    }

    @Test
    @IssueKey( "153" )
    public void shouldMapMapWithClearAndPutAll() {
        Source source = new Source();

        Map<String, Long> map = new HashMap<String, Long>();
        map.put( "Bob", 123L );
        map.put( "Alice", 456L );
        source.setOtherStringLongMap( map );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        target.getOtherStringLongMap().put( "Bill", 789L );

        assertThat( source.getOtherStringLongMap() ).hasSize( 2 );
        assertThat( target.getOtherStringLongMap() ).hasSize( 3 );

        source.getOtherStringLongMap().remove( "Alice" );

       // prepare a test list to monitor add all behaviour
        Map<String, Long> originalInstance  = new TestMap<String, Long>();
        originalInstance.putAll( target.getOtherStringLongMap() );
        TestMap.setPuttAllCalled( false );
        target.setOtherStringLongMap( originalInstance );

        SourceTargetMapper.INSTANCE.sourceToTargetTwoArg( source, target );

        assertThat( target.getOtherStringLongMap() ).isSameAs( originalInstance );
        assertThat( target.getOtherStringLongMap() ).hasSize( 1 );
        assertThat( TestMap.isPuttAllCalled() ).isTrue();
        TestMap.setPuttAllCalled( false );
    }

    @Test
    @IssueKey("87")
    public void shouldMapIntegerSetToNumberSet() {
        Set<Number> numbers = SourceTargetMapper.INSTANCE
            .integerSetToNumberSet( new HashSet<Integer>( Arrays.asList( 123, 456 ) ) );

        assertThat( numbers ).isNotNull();
        assertThat( numbers ).containsOnly( 123, 456 );
    }
}
