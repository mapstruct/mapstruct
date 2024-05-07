/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.redundant;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithClasses({
    FooMapper.class,
    FooMapperConfigured.class,
    FooSource.class,
    FooTarget.class,
    FooSourceNested.class
})
@IssueKey("3133")
public class RedundantNullCheckTest {

    private static final String EXPECTED_MAPPER_IMPL = "src/test/resources/fixtures/org/mapstruct/ap/test/nullcheck/ExpectedFooMapper.java";
    private static final String EXPECTED_MAPPER_CONFIGURED_IMPL = "src/test/resources/fixtures/org/mapstruct/ap/test/nullcheck/ExpectedFooMapperConfigured.java";

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @IssueKey("3133")
    void generatedMapperShouldNotContainAnyRedundantNullChecks() {
        generatedSource.forMapper( FooMapper.class )
            .hasSameMapperContent( FileUtils.getFile( EXPECTED_MAPPER_IMPL ) );
    }

    @ProcessorTest
    @IssueKey("3133")
    void generatedMapperConfiguredShouldNotContainAnyRedundantNullChecks() {
        generatedSource.forMapper( FooMapperConfigured.class )
            .hasSameMapperContent( FileUtils.getFile( EXPECTED_MAPPER_CONFIGURED_IMPL ) );
    }


}
