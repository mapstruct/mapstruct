/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3163;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses({
    Issue3163Mapper.class,
    Source.class,
    Target.class
})
class Issue3163Test {

    @ProcessorTest
    void shouldCompile() {
    }
}
