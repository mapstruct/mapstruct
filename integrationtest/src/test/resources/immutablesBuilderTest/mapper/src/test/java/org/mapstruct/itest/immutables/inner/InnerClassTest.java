/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.inner;

import org.junit.Test;
import org.mapstruct.itest.immutables.*;

import static org.junit.Assert.*;
import static org.mapstruct.itest.immutables.TopLevelFixture.CHILD_VALUE;

// Shows support for inner classes using all defaults
public class InnerClassTest {

    private final TopLevelDto dto = TopLevelFixture.createDto();

    private final TopLevel domain = ImmutableTopLevel.builder()
            .child(ImmutableChild.builder().string(CHILD_VALUE).build())
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
