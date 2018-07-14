/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.tests;

import org.junit.runner.RunWith;
import org.mapstruct.itest.testutil.runner.ProcessorSuite;
import org.mapstruct.itest.testutil.runner.ProcessorSuiteRunner;

/**
 * @author Filip Hrisafov
 */
@RunWith( ProcessorSuiteRunner.class )
@ProcessorSuite( baseDir = "immutablesBuilderTest",
    processorTypes = ProcessorSuite.ProcessorType.ALL_WITHOUT_PROCESSOR_PLUGIN)
public class ImmutablesBuilderTest {
}
