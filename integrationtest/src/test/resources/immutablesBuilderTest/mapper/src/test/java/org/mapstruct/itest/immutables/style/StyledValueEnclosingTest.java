/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.style;

import org.junit.Test;
import org.mapstruct.itest.immutables.TopLevelDto;
import org.mapstruct.itest.immutables.TopLevelFixture;

import static org.junit.Assert.*;
import static org.mapstruct.itest.immutables.TopLevelFixture.CHILD_VALUE;

// Shows support for @Value.Enclosing and @Value.Style
public class StyledValueEnclosingTest {
    private final TopLevelDto dto = TopLevelFixture.createDto();

    private final TopLevelWithValueEnclosingStyle domain = TopLevelWithValueEnclosingStyle.builder()
        .child( TopLevelWithValueEnclosingStyle.Child.builder()
            .string(CHILD_VALUE)
            .build())
        .build();

    @Test
    public void toImmutable() {
        assertEquals(domain, TopLevelMapper.INSTANCE.toImmutable(dto) );
    }

    @Test
    public void fromImmutable() {
        assertEquals(dto, TopLevelMapper.INSTANCE.fromImmutable(domain) );
    }
}
