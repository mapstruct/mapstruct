/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
import org.junit.Before;
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
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class, Invocation.class })
public class FieldsMappingTest {

    @Before
    public void setUp() throws Exception {
        Source.INVOCATIONS.clear();
        Target.INVOCATIONS.clear();
    }

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
        assertThat( target.fieldWithMethods ).isEqualTo( "20" );
        // Once for the null check, and the second time for the variable set
        assertThat( Source.INVOCATIONS ).containsExactly(
            new Invocation( "getFieldOnlyWithGetter" ),
            new Invocation( "getFieldOnlyWithGetter" ) );
        assertThat( Target.INVOCATIONS ).containsExactly( new Invocation( "setFieldWithMethods", "20" ) );
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
        assertThat( source.fieldOnlyWithGetter ).isEqualTo( 20 );
        assertThat( Source.INVOCATIONS ).isEmpty();
        // Once for the null check, and the second time for the variable set
        assertThat( Target.INVOCATIONS ).containsExactly(
            new Invocation( "getFieldWithMethods" ),
            new Invocation( "getFieldWithMethods" )
        );
    }
}
