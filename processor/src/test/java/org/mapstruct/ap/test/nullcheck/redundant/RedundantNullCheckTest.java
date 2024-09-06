/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.redundant;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithClasses({
    FooMapper.class,
    FooSource.class,
    FooTarget.class,
    FooSourceNested.class
})
@IssueKey("3133")
class RedundantNullCheckTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void generatedMapperShouldNotContainAnyRedundantNullChecks() {
        generatedSource.addComparisonToFixtureFor( FooMapper.class );
    }

}
