/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.context;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Stream;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({ StreamContext.class, StreamWithContextMapper.class })
@IssueKey("962")
public class StreamWithContextTest {

    @ProcessorTest
    public void shouldApplyAfterMapping() {
        Stream<String> stringStream = StreamWithContextMapper.INSTANCE.intStreamToStringStream(
            Stream.of( 1, 2, 3, 5 ) );

        assertThat( stringStream ).containsOnly( "1", "2" );
    }

    @ProcessorTest
    public void shouldApplyBeforeMappingOnArray() {
        Integer[] integers = new Integer[] { 1, 3 };
        Stream<Integer> stringStream = StreamWithContextMapper.INSTANCE.arrayToStream( integers );

        assertThat( stringStream ).containsOnly( 30, 3 );
    }

    @ProcessorTest
    public void shouldApplyBeforeAndAfterMappingOnCollection() {
        Collection<String> stringsStream = StreamWithContextMapper.INSTANCE.streamToCollection(
            Stream.of( 10, 20, 40 ) );

        assertThat( stringsStream ).containsOnly( "23", "10", "20", "40", "230" );
    }
}
