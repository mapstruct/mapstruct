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
package org.mapstruct.ap.test.abstractclass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for the generation of implementation of abstract base classes.
 *
 * @author Gunnar Morling
 */
@WithClasses({
    Source.class, Target.class, SourceTargetMapper.class, AbstractBaseMapper.class,
    BaseMapperInterface.class,
    ReferencedMapper.class,
    AbstractReferencedMapper.class,
    ReferencedMapperInterface.class,
    AbstractDto.class,
    Identifiable.class,
    HasId.class,
    AlsoHasId.class,
    Measurable.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class AbstractClassTest {

    @Test
    @IssueKey("64")
    public void shouldCreateImplementationOfAbstractMethod() {
        Source source = new Source();

        assertResult( SourceTargetMapper.INSTANCE.sourceToTarget( source ) );
    }

    @Test
    @IssueKey("165")
    public void shouldCreateImplementationOfMethodFromSuper() {
        Source source = new Source();

        assertResult( SourceTargetMapper.INSTANCE.sourceToTargetFromBaseMapper( source ) );
    }

    @Test
    @IssueKey("165")
    public void shouldCreateImplementationOfMethodFromInterface() {
        Source source = new Source();

        assertResult( SourceTargetMapper.INSTANCE.sourceToTargetFromBaseMapperInterface( source ) );
    }

    private void assertResult(Target target) {
        assertThat( target ).isNotNull();
        assertThat( target.getSize() ).isEqualTo( Long.valueOf( 181 ) );
        assertThat( target.getBirthday() ).isEqualTo( "Birthday: 26.04.1948" );
        assertThat( target.getManuallyConverted() ).isEqualTo( 42 );
        assertThat( target.isNotAttractingEqualsMethod() ).isTrue();
        assertThat( target.getId() ).isEqualTo( 42L );
    }
}
