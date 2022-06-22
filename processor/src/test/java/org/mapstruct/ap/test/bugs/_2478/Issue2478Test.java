/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2478;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2478")
@WithClasses({
    Issue2478Mapper.class
})
class Issue2478Test {

    @ProcessorTest
    void shouldGenerateCodeThatCompiles() {
        Issue2478Mapper.Product dto = new Issue2478Mapper.Product( "Test", new Issue2478Mapper.Shop( "Shopify" ) );
        Issue2478Mapper.ProductEntity entity = Issue2478Mapper.INSTANCE.productFromDto( dto, dto.getShop() );

        assertThat( entity ).isNotNull();
        assertThat( entity.getName() ).isEqualTo( "Test" );
        assertThat( entity.getShop() ).isNotNull();
        assertThat( entity.getShop().getName() ).isEqualTo( "Shopify" );
    }
}
