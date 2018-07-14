/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nonvoidsetter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
