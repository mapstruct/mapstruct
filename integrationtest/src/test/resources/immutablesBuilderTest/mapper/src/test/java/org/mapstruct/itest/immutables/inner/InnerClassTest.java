/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.inner;

import org.junit.Test;
import org.mapstruct.itest.immutables.*;

import static org.mapstruct.itest.immutables.TopLevelFixture.CHILD_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

// Shows support for inner classes using all defaults
public class InnerClassTest {

    private final TopLevelDto dto = TopLevelFixture.createDto();

    private final TopLevel domain = ImmutableTopLevel.builder()
            .child(ImmutableChild.builder().string(CHILD_VALUE).build())
            .build();

    @Test
    public void toImmutable() {
        TopLevel actual = TopLevelMapper.INSTANCE.toImmutable( dto );
        assertThat( actual.getChild().getString() ).isEqualTo( CHILD_VALUE );
    }

    @Test
    public void fromImmutable() {
        TopLevelDto actual = TopLevelMapper.INSTANCE.fromImmutable( domain );
        assertThat( actual.getChild().getString() ).isEqualTo( CHILD_VALUE );
    }
}
