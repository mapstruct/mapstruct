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
 * ECLIPSE_JDT_JAVA_8 is not working with Protobuf. Use all other available processor types.
 *
 * @author Christian Bandowski
 */
@RunWith(ProcessorSuiteRunner.class)
@ProcessorSuite(baseDir = "protobufBuilderTest",
    processorTypes = {
        ProcessorSuite.ProcessorType.ORACLE_JAVA_6,
        ProcessorSuite.ProcessorType.ORACLE_JAVA_7,
        ProcessorSuite.ProcessorType.ORACLE_JAVA_8,
        ProcessorSuite.ProcessorType.ORACLE_JAVA_9,
        ProcessorSuite.ProcessorType.ECLIPSE_JDT_JAVA_6,
        ProcessorSuite.ProcessorType.ECLIPSE_JDT_JAVA_7
    })
public class ProtobufBuilderTest {
}
