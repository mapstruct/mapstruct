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
package org.mapstruct.ap.test.imports.decorator;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.imports.decorator.other.ActorMapperDecorator;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for having a decorator in another package than the mapper interface.
 *
 * @author Gunnar Morling
 */
@IssueKey("470")
@WithClasses({
    Actor.class,
    ActorDto.class,
    ActorMapper.class,
    ActorMapperDecorator.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class DecoratorInAnotherPackageTest {

    @Test
    public void shouldApplyDecoratorFromAnotherPackage() {
        Actor actor = new Actor();
        actor.setAwards( 3 );

        ActorDto dto = ActorMapper.INSTANCE.actorToDto( actor );
        assertThat( dto ).isNotNull();
        assertThat( dto.getAwards() ).isEqualTo( 3 );
        assertThat( dto.isFamous() ).isTrue();
    }
}
