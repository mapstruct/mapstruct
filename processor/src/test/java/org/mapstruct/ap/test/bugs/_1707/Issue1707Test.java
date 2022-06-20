/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1707;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.runner.GeneratedSource;

@IssueKey("1707")
@WithClasses({
    Converter.class
})
public class Issue1707Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        Converter.class
    );

    @ProcessorTest
    public void codeShouldBeGeneratedCorrectly() {

    }
}
