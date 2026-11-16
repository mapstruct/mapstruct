/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Arrays;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1243")
@WithJSpecify
class JSpecifyCollectionPropertyTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedCollectionSourceBean.class,
        NullMarkedCollectionTargetBean.class,
        JSpecifyCollectionPropertyMapper.class
    })
    void collectionPropertyWithNonNullSourceSkipsNullCheck() {
        generatedSource.addComparisonToFixtureFor( JSpecifyCollectionPropertyMapper.class );

        NullMarkedCollectionSourceBean source = new NullMarkedCollectionSourceBean();
        source.setValues( Arrays.asList( "a", "b", "c" ) );

        NullMarkedCollectionTargetBean target = JSpecifyCollectionPropertyMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValues() ).containsExactly( "a", "b", "c" );
    }
}
