/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.builder.nestedprop.flattening;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that nested property mapping works with an immutable intermediate type.
 */
@WithClasses({
    ImmutableFlattenedStock.class,
    Stock.class,
    Article.class,
    FlatteningMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class BuilderNestedPropertyTest {

    @Test
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
