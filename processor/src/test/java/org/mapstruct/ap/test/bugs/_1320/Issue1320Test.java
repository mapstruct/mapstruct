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
package org.mapstruct.ap.test.bugs._1320;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1320")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue1320Mapper.class,
    Target.class
})
public class Issue1320Test {

    @Test
    public void shouldCreateDeepNestedConstantsCorrectly() {
        Target target = Issue1320Mapper.INSTANCE.map( 10 );

        assertThat( target.getAddress() ).isNotNull();
        assertThat( target.getAddress().getCity() ).isNotNull();
        assertThat( target.getAddress().getCity().getCityName() ).isEqualTo( "myCity" );
        assertThat( target.getAddress().getCity().getStateName() ).isEqualTo( "myState" );
    }
}
