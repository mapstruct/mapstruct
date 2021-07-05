/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.ve;

import org.junit.Test;
import org.mapstruct.itest.immutables.TopLevelDto;
import org.mapstruct.itest.immutables.TopLevelFixture;

import static org.mapstruct.itest.immutables.TopLevelFixture.CHILD_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

// shows support for @Value.Enclosing without any style
public class ValueEnclosingTest {
    private final TopLevelDto dto = TopLevelFixture.createDto();

    private final TopLevelWithValueEnclosing domain = ImmutableTopLevelWithValueEnclosing.builder()
            .child(ImmutableTopLevelWithValueEnclosing.Child.builder()
                    .string(CHILD_VALUE)
                    .build())
            .build();

    @Test
    public void toImmutable() {
        TopLevelWithValueEnclosing actual = TopLevelMapper.INSTANCE.toImmutable( dto );
        assertThat( actual.getChild().getString() ).isEqualTo( CHILD_VALUE );
    }

    @Test
    public void fromImmutable() {
        TopLevelDto actual = TopLevelMapper.INSTANCE.fromImmutable( domain );
        assertThat( actual.getChild().getString() ).isEqualTo( CHILD_VALUE );
    }
}
