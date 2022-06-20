/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.flattening;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Verifies that nested property mapping works with an immutable intermediate type.
 */
@WithClasses({
    ImmutableFlattenedStock.class,
    Stock.class,
    Article.class,
    FlatteningMapper.class
})
public class BuilderNestedPropertyTest {

    @ProcessorTest
    public void testNestedImmutablePropertyMapper() {

        Stock stock = new Stock();
        Article article1 = new Article();
        article1.setDescription( "shavingfoam" );
        Article article2 = new Article();
        article2.setDescription( "soap" );
        stock.setCount( 2 );
        stock.setFirst( article1 );
        stock.setSecond( article2 );

        ImmutableFlattenedStock flattenedTarget  = FlatteningMapper.INSTANCE.writeToFlatProperty( stock );

        assertThat( flattenedTarget ).isNotNull();
        assertThat( flattenedTarget.getArticleCount() ).isEqualTo( 2 );
        assertThat( flattenedTarget.getArticle1() ).isEqualTo( "shavingfoam" );
        assertThat( flattenedTarget.getArticle2() ).isNull();
    }
}
