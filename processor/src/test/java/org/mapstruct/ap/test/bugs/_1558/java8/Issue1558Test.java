/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1558.java8;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Sjaak Derksen
 */
@WithClasses({
    NotNull.class,
    CarMapper.class,
    Car.class,
    Car2.class
})
@IssueKey("1558")
public class Issue1558Test {

    @ProcessorTest
    public void testShouldCompile() {
        Car2 car = new Car2();
        Car target = CarMapper.INSTANCE.toCar( car );

    }

}
