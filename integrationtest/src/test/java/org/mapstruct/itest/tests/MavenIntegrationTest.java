/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.tests;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.itest.testutil.extension.ProcessorTest;

/**
 * @author Filip Hrisafov
 */
@Execution( ExecutionMode.CONCURRENT )
public class MavenIntegrationTest {

    @ProcessorTest(baseDir = "autoValueBuilderTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC,
        ProcessorTest.ProcessorType.ECLIPSE_JDT
    })
    void autoValueBuilderTest() {
    }

    @ProcessorTest(baseDir = "cdiTest")
    void cdiTest() {
    }

    /**
     * See: https://github.com/mapstruct/mapstruct/issues/1121
     */
    @ProcessorTest(baseDir = "externalbeanjar", processorTypes = ProcessorTest.ProcessorType.JAVAC)
    void externalBeanJarTest() {
    }

    @ProcessorTest(baseDir = "freeBuilderBuilderTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC,
        ProcessorTest.ProcessorType.ECLIPSE_JDT
    })
    void freeBuilderBuilderTest() {
    }

    /**
     * Integration test that compiles all test mappers in the processor-module, excluding all classes that contain
     * one of
     * the following in their path/file name:
     * <ul>
     * <li>{@code /erronerous/}</li>
     * <li>{@code *Erroneous*}</li>
     * <li>{@code *Test.java}</li>
     * <li>{@code /testutil/}</li>
     * <li>possibly more, depending on the processor type - see {@link FullFeatureCompilationExclusionCliEnhancer}</li>
     * </ul>
     */
    @ProcessorTest(baseDir = "fullFeatureTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC,
        ProcessorTest.ProcessorType.ECLIPSE_JDT
    }, commandLineEnhancer = FullFeatureCompilationExclusionCliEnhancer.class)
    void fullFeatureTest() {
    }

    @ProcessorTest(baseDir = "immutablesBuilderTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC,
        ProcessorTest.ProcessorType.ECLIPSE_JDT
    })
    void immutablesBuilderTest() {
    }

    @ProcessorTest(baseDir = "java8Test")
    void java8Test() {
    }

    @ProcessorTest(baseDir = "jaxbTest")
    void jaxbTest() {
    }

    @ProcessorTest(baseDir = "jsr330Test")
    void jsr330Test() {
    }

    @ProcessorTest(baseDir = "lombokBuilderTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    void lombokBuilderTest() {
    }

    @ProcessorTest(baseDir = "namingStrategyTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    void namingStrategyTest() {
    }

    /**
     * ECLIPSE_JDT is not working with Protobuf. Use all other available processor types.
     */
    @ProcessorTest(baseDir = "protobufBuilderTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    void protobufBuilderTest() {
    }

    @ProcessorTest(baseDir = "simpleTest")
    void simpleTest() {
    }

    @ProcessorTest(baseDir = "springTest")
    void springTest() {
    }

    /**
     * Tests usage of MapStruct with another processor that generates supertypes of mapping source/target types.
     */
    @ProcessorTest(baseDir = "superTypeGenerationTest", processorTypes = ProcessorTest.ProcessorType.JAVAC)
    void superTypeGenerationTest() {
    }

    /**
     * Tests usage of MapStruct with another processor that generates the target type of a mapping method.
     */
    @ProcessorTest(baseDir = "targetTypeGenerationTest", processorTypes = ProcessorTest.ProcessorType.JAVAC)
    void targetTypeGenerationTest() {
    }

}
