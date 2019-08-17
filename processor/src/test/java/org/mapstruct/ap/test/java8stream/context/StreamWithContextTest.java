/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.context;

import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({ StreamContext.class, StreamWithContextMapper.class })
@IssueKey("962")
@RunWith(AnnotationProcessorTestRunner.class)
public class StreamWithContextTest {

    @Test
    public void shouldApplyAfterMapping() {
        Stream<String> stringStream = StreamWithContextMapper.INSTANCE.intStreamToStringStream(
            Stream.of( 1, 2, 3, 5 ) );

        assertThat( stringStream ).containsOnly( "1", "2" );
    }

    @Test
    public void shouldApplyBeforeMappingOnArray() {
        Integer[] integers = new Integer[] { 1, 3 };
        Stream<Integer> stringStream = StreamWithContextMapper.INSTANCE.arrayToStream( integers );

        assertThat( stringStream ).containsOnly( 30, 3 );
    }

    @Test
    public void shouldApplyBeforeAndAfterMappingOnCollection() {
        Collection<String> stringsStream = StreamWithContextMapper.INSTANCE.streamToCollection(
            Stream.of( 10, 20, 40 ) );

        assertThat( stringsStream ).containsOnly( "23", "10", "20", "40", "230" );
    }
}
