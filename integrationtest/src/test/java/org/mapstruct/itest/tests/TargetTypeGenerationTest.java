/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.tests;

import org.junit.runner.RunWith;
import org.mapstruct.itest.testutil.runner.ProcessorSuite;
import org.mapstruct.itest.testutil.runner.ProcessorSuite.ProcessorType;
import org.mapstruct.itest.testutil.runner.ProcessorSuiteRunner;

/**
 * Tests usage of MapStruct with another processor that generates the target type of a mapping method.
 *
 * @author Gunnar Morling
 */
@RunWith( ProcessorSuiteRunner.class )
@ProcessorSuite(baseDir = "targetTypeGenerationTest", processorTypes = ProcessorType.ORACLE_JAVA_8)
public class TargetTypeGenerationTest {
}
