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
package org.mapstruct.ap.test.java8stream.context;

import java.util.Arrays;
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
    public void shouldApplyAfterMapping() throws Exception {
        Stream<String> stringStream = StreamWithContextMapper.INSTANCE.intStreamToStringStream(
            Arrays.asList( 1, 2, 3, 5 ).stream() );

        assertThat( stringStream ).containsOnly( "1", "2" );
    }

    @Test
    public void shouldApplyBeforeMappingOnArray() throws Exception {
        Integer[] integers = new Integer[] { 1, 3 };
        Stream<Integer> stringStream = StreamWithContextMapper.INSTANCE.arrayToStream( integers );

        assertThat( stringStream ).containsOnly( 30, 3 );
    }

    @Test
    public void shouldApplyBeforeAndAfterMappingOnCollection() throws Exception {
        Collection<String> stringsStream = StreamWithContextMapper.INSTANCE.streamToCollection(
            Arrays.asList( 10, 20, 40 ).stream() );

        assertThat( stringsStream ).containsOnly( "23", "10", "20", "40", "230" );
    }
}
