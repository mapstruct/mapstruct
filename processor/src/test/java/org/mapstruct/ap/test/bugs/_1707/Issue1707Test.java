/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1707;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1707")
@WithClasses({
    Converter.class
})
public class Issue1707Test {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        Converter.class
    );

    @Test
    public void codeShouldBeGeneratedCorrectly() {

    }
}
