/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.beforeafter;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses({
    OptionalBeforeAfterMapper.class,
    Source.class,
    Target.class })
public class OptionalBeforeAfterTest {

    @ProcessorTest
    public void dummyTest() {

    }
}
