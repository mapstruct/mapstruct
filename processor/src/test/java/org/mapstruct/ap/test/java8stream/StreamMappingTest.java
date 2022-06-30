/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

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
public class StreamMappingTest {

    @ProcessorTest
    public void shouldMapNullList() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringList() ).isNull();
    }

    @ProcessorTest
    public void shouldReverseMapNullList() {
        Target target = new Target();

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringStream() ).isNull();
    }

    @ProcessorTest
    public void shouldMapList() {
        Source source = new Source();
        source.setStringStream( Stream.of( "Bob", "Alice" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @ProcessorTest
    public void shouldMapListWithoutSetter() {
        Source source = new Source();
        source.setStringStream2( Stream.of( "Bob", "Alice" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringListNoSetter() ).containsExactly( "Bob", "Alice" );
    }

    @ProcessorTest
    public void shouldReverseMapList() {
        Target target = new Target();
        target.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringStream() ).containsExactly( "Bob", "Alice" );
    }

    @ProcessorTest
    public void shouldMapArrayList() {
        Source source = new Source();
        source.setStringArrayStream( new ArrayList<>( Arrays.asList( "Bob", "Alice" ) ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringArrayList() ).containsExactly( "Bob", "Alice" );
    }

    @ProcessorTest
    public void shouldReverseMapArrayList() {
        Target target = new Target();
        target.setStringArrayList( new ArrayList<>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringArrayStream() ).containsExactly( "Bob", "Alice" );
    }

    @ProcessorTest
    public void shouldMapSet() {
        Source source = new Source();
        source.setStringStreamToSet( new HashSet<>( Arrays.asList( "Bob", "Alice" ) ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringSet() ).contains( "Bob", "Alice" );
    }

    @ProcessorTest
    public void shouldReverseMapSet() {
        Target target = new Target();
        target.setStringSet( new HashSet<>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringStreamToSet() ).contains( "Bob", "Alice" );
    }

    @ProcessorTest
    public void shouldMapListToCollection() {
        Source source = new Source();
        source.setIntegerStream( Stream.of( 1, 2 ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getIntegerCollection() ).containsOnly( 1, 2 );
    }

    @ProcessorTest
    public void shouldReverseMapListToCollection() {
        Target target = new Target();
        target.setIntegerCollection( Arrays.asList( 1, 2 ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getIntegerStream() ).containsOnly( 1, 2 );
    }

    @ProcessorTest
    public void shouldMapIntegerSetToStringSet() {
        Source source = new Source();
        source.setAnotherIntegerStream( new HashSet<>( Arrays.asList( 1, 2 ) ).stream() );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getAnotherStringSet() ).containsOnly( "1", "2" );
    }

    @ProcessorTest
    public void shouldReverseMapIntegerSetToStringSet() {
        Target target = new Target();
        target.setAnotherStringSet( new HashSet<>( Arrays.asList( "1", "2" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getAnotherIntegerStream() ).containsOnly( 1, 2 );
    }

    @ProcessorTest
    public void shouldMapSetOfEnumToStringSet() {
        Source source = new Source();
        source.setColours( Stream.of( Colour.BLUE, Colour.GREEN ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getColours() ).containsOnly( "BLUE", "GREEN" );
    }

    @ProcessorTest
    public void shouldReverseMapSetOfEnumToStringSet() {
        Target target = new Target();
        target.setColours( new HashSet<>( Arrays.asList( "BLUE", "GREEN" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getColours() ).containsOnly( Colour.GREEN, Colour.BLUE );
    }

    @ProcessorTest
    public void shouldMapIntegerStreamToNumberSet() {
        Set<Number> numbers = SourceTargetMapper.INSTANCE
            .integerStreamToNumberSet( Stream.of( 123, 456 ) );

        assertThat( numbers ).isNotNull();
        assertThat( numbers ).containsOnly( 123, 456 );
    }

    @ProcessorTest
    public void shouldMapNonGenericList() {
        Source source = new Source();
        source.setStringStream3( new ArrayList<>( Arrays.asList( "Bob", "Alice" ) ).stream() );

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
