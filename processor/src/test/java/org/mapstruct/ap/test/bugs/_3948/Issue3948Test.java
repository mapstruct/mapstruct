/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3948;

import org.junitpioneer.jupiter.Issue;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author hduelme
 */
@Issue("3948")
public class Issue3948Test {

    @ProcessorTest
    @WithClasses(Issue3948Mapper.class)
    public void shouldRequireExactGenericMatchForDeclaredSameTypes() {

    }
}
