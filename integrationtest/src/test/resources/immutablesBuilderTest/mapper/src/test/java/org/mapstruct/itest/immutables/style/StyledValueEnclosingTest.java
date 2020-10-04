package org.mapstruct.itest.immutables.style;

import org.junit.Test;
import org.mapstruct.itest.immutables.TopLevelDto;
import org.mapstruct.itest.immutables.TopLevelFixture;
import static org.assertj.core.api.Assertions.assertThat;

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
        AbstractTopLevelWithValueEnclosingStyle actual = TopLevelMapper.INSTANCE.toImmutable( dto );
        assertThat( actual.getChild().getString() ).isEqualTo( CHILD_VALUE );
    }

    @Test
    public void fromImmutable() {
        TopLevelDto actual = TopLevelMapper.INSTANCE.fromImmutable( domain );
        assertThat( actual.getChild().getString() ).isEqualTo( CHILD_VALUE );
    }
}
