/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.inheritance;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for propagation of attributes inherited from super types.
 *
 * @author Gunnar Morling
 */
@WithClasses({ SourceBase.class, SourceExt.class, TargetBase.class, TargetExt.class, SourceTargetMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class InheritanceTest {

    @Test
    @IssueKey("17")
    public void shouldMapAttributeFromSuperType() {
        SourceExt source = createSource();

        TargetExt target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertResult( target );
    }

    @Test
    @IssueKey("19")
    public void shouldMapAttributeFromSuperTypeUsingTargetParameter() {
        SourceExt source = createSource();

        TargetExt target = new TargetExt();
        SourceTargetMapper.INSTANCE.sourceToTargetWithTargetParameter( target, source );

        assertResult( target );
    }

    @Test
    @IssueKey("19")
    public void shouldMapAttributeFromSuperTypeUsingReturnedTargetParameter() {
        SourceExt source = createSource();

        TargetExt target = new TargetExt();
        TargetBase result = SourceTargetMapper.INSTANCE.sourceToTargetWithTargetParameterAndReturn( source, target );

        assertThat( target ).isSameAs( result );

        assertResult( target );
    }

    private void assertResult(TargetExt target) {
        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 42 ) );
        assertThat( target.getBar() ).isEqualTo( 23 );
    }

    private SourceExt createSource() {
        SourceExt source = new SourceExt();
        source.setFoo( 42 );
        source.setBar( 23L );
        return source;
    }

    @Test
    @IssueKey("17")
    public void shouldReverseMapAttributeFromSuperType() {
        TargetExt target = new TargetExt();
        target.setFoo( 42L );
        target.setBar( 23 );

        SourceExt source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getFoo() ).isEqualTo( 42 );
        assertThat( source.getBar() ).isEqualTo( Long.valueOf( 23 ) );
    }
}
