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
package org.mapstruct.ap.test.java8stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@WithClasses({
    Source.class,
    Target.class,
    Colour.class,
    SourceTargetMapper.class,
    TestList.class,
    StringHolderArrayList.class,
    StringHolder.class
})
@IssueKey( "962" )
@RunWith(AnnotationProcessorTestRunner.class)
public class StreamMappingTest {

    @Test
    public void shouldMapNullList() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringList() ).isNull();
    }

    @Test
    public void shouldReverseMapNullList() {
        Target target = new Target();

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringStream() ).isNull();
    }

    @Test
    public void shouldMapList() {
        Source source = new Source();
        source.setStringStream( Arrays.asList( "Bob", "Alice" ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    public void shouldMapListWithoutSetter() {
        Source source = new Source();
        source.setStringStream2( Arrays.asList( "Bob", "Alice" ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringListNoSetter() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    public void shouldReverseMapList() {
        Target target = new Target();
        target.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringStream() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    public void shouldMapArrayList() {
        Source source = new Source();
        source.setStringArrayStream( new ArrayList<String>( Arrays.asList( "Bob", "Alice" ) ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringArrayList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    public void shouldReverseMapArrayList() {
        Target target = new Target();
        target.setStringArrayList( new ArrayList<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringArrayStream() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    public void shouldMapSet() {
        Source source = new Source();
        source.setStringStreamToSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringSet() ).contains( "Bob", "Alice" );
    }

    @Test
    public void shouldReverseMapSet() {
        Target target = new Target();
        target.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringStreamToSet() ).contains( "Bob", "Alice" );
    }

    @Test
    public void shouldMapListToCollection() {
        Source source = new Source();
        source.setIntegerStream( Arrays.asList( 1, 2 ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getIntegerCollection() ).containsOnly( 1, 2 );
    }

    @Test
    public void shouldReverseMapListToCollection() {
        Target target = new Target();
        target.setIntegerCollection( Arrays.asList( 1, 2 ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getIntegerStream() ).containsOnly( 1, 2 );
    }

    @Test
    public void shouldMapIntegerSetToStringSet() {
        Source source = new Source();
        source.setAnotherIntegerStream( new HashSet<Integer>( Arrays.asList( 1, 2 ) ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getAnotherStringSet() ).containsOnly( "1", "2" );
    }

    @Test
    public void shouldReverseMapIntegerSetToStringSet() {
        Target target = new Target();
        target.setAnotherStringSet( new HashSet<String>( Arrays.asList( "1", "2" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getAnotherIntegerStream() ).containsOnly( 1, 2 );
    }

    @Test
    public void shouldMapSetOfEnumToStringSet() {
        Source source = new Source();
        source.setColours( EnumSet.of( Colour.BLUE, Colour.GREEN ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getColours() ).containsOnly( "BLUE", "GREEN" );
    }

    @Test
    public void shouldReverseMapSetOfEnumToStringSet() {
        Target target = new Target();
        target.setColours( new HashSet<String>( Arrays.asList( "BLUE", "GREEN" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getColours() ).containsOnly( Colour.GREEN, Colour.BLUE );
    }

    @Test
    public void shouldMapIntegerStreamToNumberSet() {
        Set<Number> numbers = SourceTargetMapper.INSTANCE
            .integerStreamToNumberSet( Arrays.asList( 123, 456 ).stream() );

        assertThat( numbers ).isNotNull();
        assertThat( numbers ).containsOnly( 123, 456 );
    }

    @Test
    public void shouldMapNonGenericList() {
        Source source = new Source();
        source.setStringStream3( new ArrayList<String>( Arrays.asList( "Bob", "Alice" ) ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNonGenericStringList() ).containsExactly(
            new StringHolder( "Bob" ),
            new StringHolder( "Alice" )
        );

        // Inverse direction
        Target newTarget = new Target();
        StringHolderArrayList nonGenericStringList = new StringHolderArrayList();
        nonGenericStringList.addAll( Arrays.asList( new StringHolder( "Bill" ), new StringHolder( "Bob" ) ) );
        newTarget.setNonGenericStringList( nonGenericStringList );

        Source mappedSource = SourceTargetMapper.INSTANCE.targetToSource( newTarget );

        assertThat( mappedSource ).isNotNull();
        assertThat( mappedSource.getStringStream3() ).containsExactly( "Bill", "Bob" );
    }
}
