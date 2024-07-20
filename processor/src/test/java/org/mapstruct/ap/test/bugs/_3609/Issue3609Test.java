/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3609;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Roman Obolonskyii
 */
@IssueKey("3609")
@WithClasses(Issue3609Mapper.class)
public class Issue3609Test {

    @ProcessorTest
    void shouldCompileWithoutErrors() {

    }
}
