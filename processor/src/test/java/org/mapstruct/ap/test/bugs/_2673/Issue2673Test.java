/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2673;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;

/**
 * @author Ben Zegveld
 */
@WithClasses({
    Issue2673Mapper.class
})
class Issue2673Test {

    @ProcessorTest( Compiler.JDK )
    @IssueKey( "2673" )
    void shouldcompileWithTheJdkCompiler() {
        // This issue does not cause a problem for the eclipse compiler.
    }
}
