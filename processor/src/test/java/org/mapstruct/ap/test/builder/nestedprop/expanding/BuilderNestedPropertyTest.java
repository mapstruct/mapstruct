/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.expanding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that nested property mapping works with an immutable intermediate type.
 */
@WithClasses({
    FlattenedStock.class,
    ImmutableExpandedStock.class,
    ImmutableArticle.class,
    ExpandingMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class BuilderNestedPropertyTest {

    @Test
    public void testNestedImmutablePropertyMapper() {
        FlattenedStock stock = new FlattenedStock( "Sock", "Tie", 33 );
        ImmutableExpandedStock expandedTarget = ExpandingMapper.INSTANCE.writeToNestedProperty( stock );
        assertThat( expandedTarget ).isNotNull();
        assertThat( expandedTarget.getCount() ).isEqualTo( 33 );
        assertThat( expandedTarget.getSecond() ).isNull();
        assertThat( expandedTarget.getFirst() ).isNotNull();
        assertThat( expandedTarget.getFirst().getDescription() ).isEqualTo( "Sock" );
    }
}
