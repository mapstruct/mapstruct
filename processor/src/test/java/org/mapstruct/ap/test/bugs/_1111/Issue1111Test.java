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
package org.mapstruct.ap.test.bugs._1111;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._1111.Issue1111Mapper.Source;
import org.mapstruct.ap.test.bugs._1111.Issue1111Mapper.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "1111")
@WithClasses({Issue1111Mapper.class})
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue1111Test {

    @Test
    public void shouldCompile() {

        List<List<Source>> source = Arrays.asList( Arrays.asList( new Source() ) );

        List<List<Issue1111Mapper.Target>> target = Issue1111Mapper.INSTANCE.listList( source );

        assertThat( target ).hasSize( 1 );
        assertThat( target.get( 0 ) ).hasSize( 1 );
        assertThat( target.get( 0 ).get( 0 ) ).isInstanceOf( Target.class );
    }
}
