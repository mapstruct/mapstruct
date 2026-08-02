/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.collectiontarget;

import java.util.Arrays;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey( "4057" )
@WithJSpecify
@WithClasses( {
    CollectionTargetNullCheckSourceBean.class,
    CollectionTargetNullCheckTargetBean.class,
    CollectionTargetNullCheckMapper.class
} )
class CollectionTargetNullCheckTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void nullSourceListShouldNotBePassedToNonNullSetter() {
        generatedSource.addComparisonToFixtureFor( CollectionTargetNullCheckMapper.class );

        CollectionTargetNullCheckSourceBean source = new CollectionTargetNullCheckSourceBean();
        source.setNumbers( null );

        CollectionTargetNullCheckTargetBean target = CollectionTargetNullCheckMapper.INSTANCE.map( source );

        // Per docs (chapter-10-advanced-mapping-options.asciidoc, "Property-level rules"):
        // "If the target is @NonNull (and the source is not @NonNull), a null check is always
        // added so the target's contract is not violated."
        // setNumbers() is annotated @NonNull, so it should never be invoked when the source is null.
        assertThat( target.isNumbersSet() ).isFalse();
    }

    @ProcessorTest
    void nonNullSourceListShouldBeMappedToNonNullSetter() {
        generatedSource.addComparisonToFixtureFor( CollectionTargetNullCheckMapper.class );

        CollectionTargetNullCheckSourceBean source = new CollectionTargetNullCheckSourceBean();
        source.setNumbers( Arrays.asList( 1, 2, 3 ) );

        CollectionTargetNullCheckTargetBean target = CollectionTargetNullCheckMapper.INSTANCE.map( source );

        assertThat( target.isNumbersSet() ).isTrue();
        assertThat( target.getNumbers() ).containsExactly( "1", "2", "3" );
    }
}
