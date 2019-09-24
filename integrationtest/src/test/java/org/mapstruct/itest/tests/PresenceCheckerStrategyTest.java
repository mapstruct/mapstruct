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
 * @author Kirill Baurchanu
 */
@RunWith( ProcessorSuiteRunner.class )
@ProcessorSuite( baseDir = "presenceCheckerStrategyTest", processorTypes = ProcessorSuite.ProcessorType.ORACLE_JAVA_8 )
public class PresenceCheckerStrategyTest {
}
