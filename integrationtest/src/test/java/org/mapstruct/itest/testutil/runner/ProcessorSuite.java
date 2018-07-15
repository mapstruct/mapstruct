/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.testutil.runner;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import org.apache.maven.it.Verifier;

/**
 * Declares the content of the integration test.
 * <p />
 * {@link #baseDir()} must be a path in the classpath that contains the maven module to run as integration test. The
 * integration test module should contain at least one test class. The integration test passes, if
 * {@code mvn clean test} finishes successfully.
 * <p />
 * {@link #processorTypes()} configures the variants to execute the integration tests with. See {@link ProcessorType}.
 *
 * @author Andreas Gudian
 */
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Target( ElementType.TYPE )
public @interface ProcessorSuite {
    /**
     * Describes the type of the processing variant(s) to use when executing the integration test.
     * <p />
     * Types that require <a href="http://maven.apache.org/guides/mini/guide-using-toolchains.html">toolchains</a>, will
     * need the toolchains.xml file to be either installed in ~/m2, or alternatively passed to the mvn process using
     * {@code mvn -DprocessorIntegrationTest.toolchainsFile=/path/to/toolchains.xml ...}
     *
     * @author Andreas Gudian
     */
    enum ProcessorType {
        /**
         * Use an Oracle JDK 1.6 (or 1.6.x) via toolchain support to perform the processing
         */
        ORACLE_JAVA_6( new Toolchain( "oracle", "1.6", "1.7" ), "javac", "1.6" ),

        /**
         * Use an Oracle JDK 1.7 (or 1.7.x) via toolchain support to perform the processing
         */
        ORACLE_JAVA_7( new Toolchain( "oracle", "1.7", "1.8" ), "javac", "1.7" ),

        /**
         * Use the same JDK that runs the mvn build to perform the processing
         */
        ORACLE_JAVA_8( null, "javac", "1.8" ),

        /**
         * Use an Oracle JDK 1.9 (or 1.9.x) via toolchain support to perform the processing
         */
        ORACLE_JAVA_9( new Toolchain( "oracle", "9", "10" ), "javac", "1.9" ),

        /**
         * Use the eclipse compiler with 1.6 source/target level from tycho-compiler-jdt to perform the build and
         * processing
         */
        ECLIPSE_JDT_JAVA_6( null, "jdt", "1.6" ),

        /**
         * Use the eclipse compiler with 1.7 source/target level from tycho-compiler-jdt to perform the build and
         * processing
         */
        ECLIPSE_JDT_JAVA_7( null, "jdt", "1.7" ),

        /**
         * Use the eclipse compiler with 1.8 source/target level from tycho-compiler-jdt to perform the build and
         * processing
         */
        ECLIPSE_JDT_JAVA_8( null, "jdt", "1.8" ),

        /**
         * Use the maven-processor-plugin with 1.8 source/target level with the same JDK that runs the mvn build to
         * perform the processing
         */
        PROCESSOR_PLUGIN_JAVA_8( null, null, "1.8" ),

        /**
         * Use all processing variants, but without the maven-procesor-plugin
         */
        ALL_WITHOUT_PROCESSOR_PLUGIN(ORACLE_JAVA_6, ORACLE_JAVA_7, ORACLE_JAVA_8, ORACLE_JAVA_9, ECLIPSE_JDT_JAVA_6,
            ECLIPSE_JDT_JAVA_7, ECLIPSE_JDT_JAVA_8),

        /**
         * Use all available processing variants
         */
        ALL( ORACLE_JAVA_6, ORACLE_JAVA_7, ORACLE_JAVA_8, ORACLE_JAVA_9, ECLIPSE_JDT_JAVA_6, ECLIPSE_JDT_JAVA_7,
            ECLIPSE_JDT_JAVA_8, PROCESSOR_PLUGIN_JAVA_8 ),

        /**
         * Use all JDK8 compatible processing variants
         */
        ALL_JAVA_8( ORACLE_JAVA_8, ECLIPSE_JDT_JAVA_8, PROCESSOR_PLUGIN_JAVA_8 );

        private ProcessorType[] included = { };

        private Toolchain toolchain;
        private String compilerId;
        private String sourceTargetVersion;

        ProcessorType(Toolchain toolchain, String compilerId, String sourceTargetVersion) {
            this.toolchain = toolchain;
            this.compilerId = compilerId;
            this.sourceTargetVersion = sourceTargetVersion;
        }

        ProcessorType(ProcessorType... included) {
            this.included = included;
        }

        /**
         * @return the processor types that are grouped by this type
         */
        public ProcessorType[] getIncluded() {
            return included;
        }

        /**
         * @return the toolchain
         */
        public Toolchain getToolchain() {
            return toolchain;
        }

        /**
         * @return the compilerId
         */
        public String getCompilerId() {
            return compilerId;
        }

        /**
         * @return the sourceTargetVersion
         */
        public String getSourceTargetVersion() {
            return sourceTargetVersion;
        }
    }

    /**
     * Can be configured to provide additional command line arguments for the invoked Maven process, depending on the
     * {@link ProcessorType} the test is executed for.
     *
     * @author Andreas Gudian
     */
    interface CommandLineEnhancer {
        /**
         * @param processorType the processor type for which the test is executed.
         * @return additional command line arguments to be passed to the Maven {@link Verifier}.
         */
        Collection<String> getAdditionalCommandLineArguments(ProcessorType processorType);
    }

    /**
     * @return a path in the classpath that contains the maven module to run as integration test: {@code mvn clean test}
     */
    String baseDir();

    /**
     * @return the variants to execute the integration tests with. See {@link ProcessorType}.
     */
    ProcessorType[] processorTypes() default { ProcessorType.ALL };

    /**
     * @return the {@link CommandLineEnhancer} implementation. Must have a default constructor.
     */
    Class<? extends CommandLineEnhancer> commandLineEnhancer() default CommandLineEnhancer.class;
}
