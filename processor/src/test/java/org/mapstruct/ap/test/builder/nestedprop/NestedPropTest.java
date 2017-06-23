package org.mapstruct.ap.test.builder.nestedprop;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses( {FlattenedSource.class, ExpandedTarget.class, ImmutableTargetContainer.class} )
@Category(BuilderTests.class)
public class NestedPropTest {
    @Test
    @WithClasses( {FlattenedMapper.class} )
    public void testNestedProp_HappyPath() {

    }
}
