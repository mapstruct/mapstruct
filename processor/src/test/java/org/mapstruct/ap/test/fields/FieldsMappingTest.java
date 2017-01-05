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
package org.mapstruct.ap.test.fields;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey( "557" )
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
public class FieldsMappingTest {

    @Test
    public void shouldMapSourceToTarget() throws Exception {
        Source source = new Source();
        source.normalInt = 4;
        source.normalList = Lists.newArrayList( 10, 11, 12 );
        source.fieldOnlyWithGetter = 20;

        Target target = SourceTargetMapper.INSTANCE.toSource( source );

        assertThat( target ).isNotNull();
        assertThat( target.finalInt ).isEqualTo( "10" );
        assertThat( target.normalInt ).isEqualTo( "4" );
        assertThat( target.finalList ).containsOnly( "1", "2", "3" );
        assertThat( target.normalList ).containsOnly( "10", "11", "12" );
        assertThat( target.privateFinalList ).containsOnly( 3, 4, 5 );
        // +21 from the source getter and append 11 on the setter from the target
        assertThat( target.fieldWithMethods ).isEqualTo( "4111" );
    }

    @Test
    public void shouldMapTargetToSource() throws Exception {
        Target target = new Target();
        target.finalInt = "40";
        target.normalInt = "4";
        target.finalList = Lists.newArrayList( "2", "3" );
        target.normalList = Lists.newArrayList( "10", "11", "12" );
        target.privateFinalList = Lists.newArrayList( 10, 11, 12 );
        target.fieldWithMethods = "20";

        Source source = SourceTargetMapper.INSTANCE.toSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.finalInt ).isEqualTo( 10 );
        assertThat( source.normalInt ).isEqualTo( 4 );
        assertThat( source.finalList ).containsOnly( 1, 2, 3 );
        assertThat( source.normalList ).containsOnly( 10, 11, 12 );
        assertThat( source.getPrivateFinalList() ).containsOnly( 3, 4, 5, 10, 11, 12 );
        // 23 is appended on the target getter
        assertThat( source.fieldOnlyWithGetter ).isEqualTo( 2023 );
    }
}
