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
package org.mapstruct.ap.test.oneway;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for propagation of attribute without setter in source and getter in
 * target.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class OnewayTest {

    @Test
    @IssueKey("17")
    public void shouldMapAttributeWithoutSetterInSourceType() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.retrieveFoo() ).isEqualTo( Long.valueOf( 42 ) );
    }

    @Test
    @IssueKey("41")
    public void shouldReverseMapAttributeWithoutSetterInTargetType() {
        Target target = new Target();

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.retrieveBar() ).isEqualTo( 23 );
    }

    @Test
    @IssueKey("104")
    public void shouldMapMappedAttributeWithoutSetterInSourceType() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getQux() ).isEqualTo( 23L );
    }
}
