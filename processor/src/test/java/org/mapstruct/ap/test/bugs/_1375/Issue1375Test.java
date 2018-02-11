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
package org.mapstruct.ap.test.bugs._1375;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses( {
    Target.class,
    Source.class,
    Issue1375Mapper.class
} )
@RunWith( AnnotationProcessorTestRunner.class )
@IssueKey( "1375" )
public class Issue1375Test {

    @Test
    public void shouldGenerateCorrectMapperWhenIntermediaryReadAccessorIsMissing() {

        Target target = Issue1375Mapper.INSTANCE.map( new Source( "test value" ) );

        assertThat( target ).isNotNull();
        assertThat( target.nested ).isNotNull();
        assertThat( target.nested.getValue() ).isEqualTo( "test value" );
    }
}
