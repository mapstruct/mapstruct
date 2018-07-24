/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1558.java8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.Compiler;
import org.mapstruct.ap.testutil.runner.WithSingleCompiler;

/**
 * @author Sjaak Derksen
 */
@WithClasses({
    NotNull.class,
    CarMapper.class,
    Car.class,
    Car2.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1558")
@WithSingleCompiler( Compiler.JDK )
public class Issue1558Test {

    @Test
    public void testShouldCompile() {
        Car2 car = new Car2();
        Car target = CarMapper.INSTANCE.toCar( car );

    }

}
