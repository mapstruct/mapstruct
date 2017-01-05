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
package org.mapstruct.ap.test.java8stream.base;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("962")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Source.class,
    Target.class,
    StreamMapper.class,
    MyCustomException.class,
    TargetElement.class,
    SourceElement.class
})
public class StreamsTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    public void shouldNotContainFunctionIdentity() throws Exception {
        generatedSource.forMapper( StreamMapper.class )
            .content()
            .as( "The Mapper implementation should not use Function.identity()" )
            .doesNotContain( "Function.identity()" );
    }

    @Test
    public void shouldMapSourceStream() throws Exception {
        List<Integer> someInts = Arrays.asList( 1, 2, 3 );
        Stream<Integer> stream = someInts.stream();
        Source source = new Source();
        source.setStream( stream );
        source.setStringStream( Arrays.asList( "4", "5", "6", "7" ).stream() );
        source.setInts( Arrays.asList( 1, 2, 3 ) );
        source.setIntegerSet( Arrays.asList( 1, 1, 2, 2, 4, 4 ).stream() );
        source.setStringCollection( Arrays.asList( "1", "1", "2", "3" ).stream().distinct() );
        source.setIntegerIterable( Arrays.asList( 10, 11, 12 ).stream() );
        source.setSortedSet( Arrays.asList( 12, 11, 10 ).stream() );
        source.setNavigableSet( Arrays.asList( 12, 11, 10 ).stream() );
        source.setIntToStringStream( Arrays.asList( 10, 11, 12 ).stream() );
        source.setStringArrayStream( Arrays.asList( "4", "5", "6", "6" ).stream().limit( 2 ) );

        SourceElement element = new SourceElement();
        element.setSource( "source1" );
        source.setSourceElements( Arrays.asList( element ).stream() );


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

    @Test
    public void shouldMapTargetStream() throws Exception {
        List<Integer> someInts = Arrays.asList( 1, 2, 3 );
        Stream<Integer> stream = someInts.stream();
        Target target = new Target();
        target.setTargetStream( stream );
        target.setStringStream( Arrays.asList( "4", "5", "6", "7" ) );
        target.setInts( Arrays.asList( 1, 2, 3 ).stream() );
        target.setIntegerSet( Collections.asSet( 1, 1, 2, 2, 4, 4 ) );
        target.setStringCollection( Collections.asSet( "1", "1", "2", "3" ) );
        target.setIntegerIterable( Arrays.asList( 10, 11, 12 ) );
        target.setSortedSet( new TreeSet<Integer>( Arrays.asList( 12, 11, 10 ) ) );
        target.setNavigableSet( new TreeSet<Integer>( Arrays.asList( 12, 11, 10 ) ) );
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
