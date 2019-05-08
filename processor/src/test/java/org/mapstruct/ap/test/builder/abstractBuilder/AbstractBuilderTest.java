/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test is for abstract builders, where some of the target properties are written by the abstract
 * builder class, and some of the properties are written by the concrete builder implementation.
 */
@WithClasses({
    AbstractProductBuilder.class,
    AbstractImmutableProduct.class,
    ImmutableProduct.class,
    ProductDto.class,
    ProductMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class AbstractBuilderTest {

    /**
     * This test verifies that:
     * WHEN - a mapping method's return type is an immutable/built target, AND
     * - the mapped properties are split between the abstract and concrete builder, AND
     * THEN
     * - the builder is "discovered"
     * - all values are mapped
     * - all values are set properly
     */
    @Test
    public void testThatAbstractBuilderMapsAllProperties() {
        ImmutableProduct product = ProductMapper.INSTANCE.fromMutable( new ProductDto( "router", 31 ) );

        assertThat( product.getPrice() ).isEqualTo( 31 );
        assertThat( product.getName() ).isEqualTo( "router" );
        assertThat( product.getSettlementPrice() ).isEqualTo( 31 );
        assertThat( product.getIssuer() ).isTrue();
    }

    @Test
    public void testThatAbstractBuilderReverseMapsAllProperties() {
        ProductDto product = ProductMapper.INSTANCE.fromImmutable( ImmutableProduct.builder()
            .price( 31000 )
            .name( "car" )
            .build() );

        assertThat( product.getPrice() ).isEqualTo( 31000 );
        assertThat( product.getName() ).isEqualTo( "car" );
    }
}
