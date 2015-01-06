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
package org.mapstruct.ap.test.nonvoidsetter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for using non-void setters (fluent style) in the target.
 *
 * @author Gunnar Morling
 */
@WithClasses({ Actor.class, ActorDto.class, ActorMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class NonVoidSettersTest {

    @Test
    @IssueKey("353")
    public void shouldMapAttributeWithoutSetterInSourceType() {
        ActorDto target = ActorMapper.INSTANCE.actorToActorDto( new Actor( 3, "Hickory Black" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "Hickory Black" );
        assertThat( target.getOscars() ).isEqualTo( 3 );
    }
}
