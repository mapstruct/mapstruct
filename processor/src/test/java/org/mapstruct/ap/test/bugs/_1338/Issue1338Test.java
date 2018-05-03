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
package org.mapstruct.ap.test.bugs._1338;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1338")
@WithClasses({
    Issue1338Mapper.class,
    Source.class,
    Target.class
})
public class Issue1338Test {

    @Test
    public void shouldCorrectlyUseAdder() {
        Source source = new Source();
        source.setProperties( Arrays.asList( "first", "second" ) );
        Target target = Issue1338Mapper.INSTANCE.map( source );

        assertThat( target )
            .extracting( "properties" )
            .contains( Arrays.asList( "first", "second" ), atIndex( 0 ) );

        Source mapped = Issue1338Mapper.INSTANCE.map( target );

        assertThat( mapped.getProperties() )
            .containsExactly( "first", "second" );
    }
}
