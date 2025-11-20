/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.tests;

import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
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
        ProcessorTest.ProcessorType.JAVAC
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

    @ProcessorTest(baseDir = "jakartaJaxbTest")
    void jakartaJaxbTest() {
    }

    @ProcessorTest(baseDir = "jsr330Test")
    @EnabledForJreRange(min = JRE.JAVA_17)
    @DisabledOnJre(JRE.OTHER)
    void jsr330Test() {
    }

    @ProcessorTest(baseDir = "lombokBuilderTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    @DisabledOnJre(JRE.OTHER)
    void lombokBuilderTest() {
    }

    @ProcessorTest(baseDir = "lombokModuleTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC,
        ProcessorTest.ProcessorType.JAVAC_WITH_PATHS
    })
    @EnabledForJreRange(min = JRE.JAVA_11)
    @DisabledOnJre(JRE.OTHER)
    void lombokModuleTest() {
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

    @ProcessorTest(baseDir = "sealedSubclassTest")
    @EnabledForJreRange(min = JRE.JAVA_17)
    void sealedSubclassTest() {
    }

    @ProcessorTest(baseDir = "recordsTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    @EnabledForJreRange(min = JRE.JAVA_14)
    void recordsTest() {
    }

    @ProcessorTest(baseDir = "recordsCrossModuleTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    @EnabledForJreRange(min = JRE.JAVA_17)
    void recordsCrossModuleTest() {
    }

    @ProcessorTest(baseDir = "recordsCrossModuleInterfaceTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    @EnabledForJreRange(min = JRE.JAVA_17)
    void recordsCrossModuleInterfaceTest() {
    }

    @ProcessorTest(baseDir = "expressionTextBlocksTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    @EnabledForJreRange(min = JRE.JAVA_17)
    void expressionTextBlocksTest() {
    }

    @ProcessorTest(baseDir = "kotlinDataTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    }, forkJvm = true)
    // We have to fork the jvm because there is an NPE in com.intellij.openapi.util.SystemInfo.getRtVersion
    // and the kotlin-maven-plugin uses that. See also https://youtrack.jetbrains.com/issue/IDEA-238907
    @DisabledOnJre(JRE.OTHER)
    void kotlinDataTest() {
    }

    @ProcessorTest(baseDir = "simpleTest")
    void simpleTest() {
    }

    // for issue #2593
    @ProcessorTest(baseDir = "defaultPackage")
    void defaultPackageTest() {
    }

    @ProcessorTest(baseDir = "springTest")
    @EnabledForJreRange(min = JRE.JAVA_17)
    @DisabledOnJre(JRE.OTHER)
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

    @ProcessorTest(baseDir = "moduleInfoTest")
    @EnabledForJreRange(min = JRE.JAVA_11)
    void moduleInfoTest() {

    }

    /**
     * Tests usage of MapStruct with another processor that generates the uses type of a mapper.
     */
    @ProcessorTest(baseDir = "usesTypeGenerationTest", processorTypes = {
        ProcessorTest.ProcessorType.JAVAC
    })
    void usesTypeGenerationTest() {
    }

    /**
     * Tests usage of MapStruct with another processor that generates the uses type of a mapper.
     */
    @ProcessorTest(baseDir = "usesTypeGenerationTest", processorTypes = {
        ProcessorTest.ProcessorType.ECLIPSE_JDT
    })
    @EnabledForJreRange(min = JRE.JAVA_11)
    // For some reason the second run with eclipse does not load the ModelElementProcessor(s) on java 8,
    // therefore we run this only on Java 11
    void usesTypeGenerationTestEclipse() {
    }

    /**
     * Tests usage of MapStruct with faulty provider of AstModifyingAnnotationProcessor.
     */
    @ProcessorTest(baseDir = "faultyAstModifyingAnnotationProcessorTest")
    void faultyAstModifyingProcessor() {
    }

}
