/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.decorator;

import static org.assertj.core.api.Assertions.assertThat;

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
