/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3747;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3747")
@WithClasses(Issue3747Mapper.class)
class Issue3747Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void shouldNotGenerateObsoleteCode() {
        generatedSource.addComparisonToFixtureFor( Issue3747Mapper.class );
    }
}
