/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3886.jdk21;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;

/**
 * @author Filip Hrisafov
 */
@WithClasses(Issue3886Mapper.class)
@IssueKey("3886")
class Issue3886Test {

    // The current version of the Eclipse compiler we use does not support records
    @ProcessorTest(Compiler.JDK)
    void shouldCompile() {

    }
}
