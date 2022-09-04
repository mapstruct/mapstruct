/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2907;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.bugs._2907.mapper.Issue2907Mapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2907")
@WithClasses({
    Issue2907Mapper.class,
    Source.class,
    SourceNested.class,
    Target.class,
})
class Issue2907Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void shouldNotGeneratedImportForNestedClass() {
        generatedSource.forMapper( Issue2907Mapper.class )
            .containsNoImportFor( Target.TargetNested.class );
    }
}
