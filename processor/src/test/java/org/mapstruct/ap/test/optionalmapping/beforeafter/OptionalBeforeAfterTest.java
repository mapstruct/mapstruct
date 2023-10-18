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
