/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3747;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Issue3747Test {

    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( ICategoryMapper.class );

    @ProcessorTest
    @WithClasses({
        ICategoryMapper.class,
        Category.class,
        ApiCategoryRecord.class
    })
    void shouldNotGenerateEmptyIfConditionWhenPropertyMappingsAreEmpty() {
        Category category = new Category( 123L, "Category 1" );

        ApiCategoryRecord apiCategoryRecord = ICategoryMapper.INSTANCE.mapRecord( category );

        assertThat( apiCategoryRecord.getId() ).isEqualTo( 123L );
        assertThat( apiCategoryRecord.getTitle() ).isEqualTo( "Category 1" );
    }

}
