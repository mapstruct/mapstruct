/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.base;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("962")
@WithClasses({
    Source.class,
    Target.class,
    StreamMapper.class,
    MyCustomException.class,
    TargetElement.class,
    SourceElement.class
})
public class StreamsTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldNotContainFunctionIdentity() {
        generatedSource.forMapper( StreamMapper.class )
            .content()
            .as( "The Mapper implementation should not use Function.identity()" )
            .doesNotContain( "Function.identity()" );
    }

    @ProcessorTest
    public void shouldMapSourceStream() {
        List<Integer> someInts = Arrays.asList( 1, 2, 3 );
        Stream<Integer> stream = someInts.stream();
        Source source = new Source();
        source.setStream( stream );
        source.setStringStream( Stream.of( "4", "5", "6", "7" ) );
        source.setInts( Arrays.asList( 1, 2, 3 ) );
        source.setIntegerSet( Stream.of( 1, 1, 2, 2, 4, 4 ) );
        source.setStringCollection( Stream.of( "1", "1", "2", "3" ).distinct() );
        source.setIntegerIterable( Stream.of( 10, 11, 12 ) );
        source.setSortedSet( Stream.of( 12, 11, 10 ) );
        source.setNavigableSet( Stream.of( 12, 11, 10 ) );
        source.setIntToStringStream( Stream.of( 10, 11, 12 ) );
        source.setStringArrayStream( Stream.of( "4", "5", "6", "6" ).limit( 2 ) );

        SourceElement element = new SourceElement();
        element.setSource( "source1" );
        source.setSourceElements( Stream.of( element ) );


        Target target = StreamMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getTargetStream() ).isSameAs( stream );
        assertThat( target.getStringStream() ).containsExactly( "4", "5", "6", "7" );
        assertThat( target.getInts() ).containsExactly( 1, 2, 3 );
        assertThat( target.getIntegerSet() ).containsOnly( 1, 2, 4 );
        assertThat( target.getStringCollection() ).containsExactly( "1", "2", "3" ).isInstanceOf( List.class );
        assertThat( target.getIntegerIterable() ).containsExactly( 10, 11, 12 ).isInstanceOf( List.class );
        assertThat( target.getSortedSet() ).containsExactly( 10, 11, 12 ).isInstanceOf( TreeSet.class );
        assertThat( target.getNavigableSet() ).containsExactly( 10, 11, 12 ).isInstanceOf( TreeSet.class );
        assertThat( target.getIntToStringStream() ).containsExactly( "10", "11", "12" );
        assertThat( target.getStringArrayStream() ).containsExactly( 4, 5 );
        assertThat( target.getTargetElements().get( 0 ).getSource() ).isEqualTo( "source1" );
    }

    @ProcessorTest
    public void shouldMapTargetStream() {
        List<Integer> someInts = Arrays.asList( 1, 2, 3 );
        Stream<Integer> stream = someInts.stream();
        Target target = new Target();
        target.setTargetStream( stream );
        target.setStringStream( Arrays.asList( "4", "5", "6", "7" ) );
        target.setInts( Stream.of( 1, 2, 3 ) );
        target.setIntegerSet( Collections.asSet( 1, 1, 2, 2, 4, 4 ) );
        target.setStringCollection( Collections.asSet( "1", "1", "2", "3" ) );
        target.setIntegerIterable( Arrays.asList( 10, 11, 12 ) );
        target.setSortedSet( new TreeSet<>( Arrays.asList( 12, 11, 10 ) ) );
        target.setNavigableSet( new TreeSet<>( Arrays.asList( 12, 11, 10 ) ) );
        target.setIntToStringStream( Arrays.asList( "4", "5", "6" ) );
        target.setStringArrayStream( new Integer[] { 10, 11, 12 } );


        Source source = StreamMapper.INSTANCE.map( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStream() ).isSameAs( stream );
        assertThat( source.getStringStream() ).containsExactly( "4", "5", "6", "7" );
        assertThat( source.getInts() ).containsExactly( 1, 2, 3 );
        assertThat( source.getIntegerSet() ).containsOnly( 1, 2, 4 );
        assertThat( source.getStringCollection() ).containsExactly( "1", "2", "3" );
        assertThat( source.getIntegerIterable() ).containsExactly( 10, 11, 12 );
        assertThat( source.getSortedSet() ).containsExactly( 10, 11, 12 );
        assertThat( source.getNavigableSet() ).containsExactly( 10, 11, 12 );
        assertThat( source.getIntToStringStream() ).containsExactly( 4, 5, 6 );
        assertThat( source.getStringArrayStream() ).containsExactly( "10", "11", "12" );
    }
}
