/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._775;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.assertj.core.api.IterableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Verifies:
 * <ul>
 * <li>For target properties of type {@link Iterable}, a forged method can be created.
 * <li>For target properties of type {@code Iterable<? extends Integer>}, a custom mapping method that returns
 * {@code List<Integer>} is chosen as property mapping method.
 * </ul>
 *
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("775")
@WithClasses({
    MapperWithForgedIterableMapping.class,
    MapperWithCustomListMapping.class,
    ListContainer.class,
    IterableContainer.class
})
public class IterableWithBoundedElementTypeTest {

    @Test
    public void createsForgedMethodForIterableLowerBoundInteger() {
        ListContainer source = new ListContainer();

        source.setValues( Arrays.asList( "42", "47" ) );
        IterableContainer result = MapperWithForgedIterableMapping.INSTANCE.toContainerWithIterable( source );

        ( (IterableAssert<Integer>) assertThat( result.getValues() ) )
                .contains( 42, 47 );
    }

    @Test
    public void usesListIntegerMethodForIterableLowerBoundInteger() {
        ListContainer source = new ListContainer();

        source.setValues( Arrays.asList( "42", "47" ) );
        IterableContainer result = MapperWithCustomListMapping.INSTANCE.toContainerWithIterable( source );

        ( (IterableAssert<Integer>) assertThat( result.getValues() ) )
                .contains( 66, 71 );
    }
}
