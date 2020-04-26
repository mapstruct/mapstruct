/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.testutil.extension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import org.apache.maven.it.Verifier;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Declares the content of the integration test.
 * <p />
 * {@link #baseDir()} must be a path in the classpath that contains the maven module to run as integration test. The
 * integration test module should contain at least one test class. The integration test passes, if
 * {@code mvn clean test} finishes successfully.
 * <p />
 * {@link #processorTypes()} configures the variants to execute the integration tests with. See
 * {@link ProcessorType}.
 *
 * @author Filip Hrisafov
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TestTemplate
@ExtendWith(ProcessorTestTemplateInvocationContextProvider.class)
public @interface ProcessorTest {

    /**
     * Describes the type of the processing variant(s) to use when executing the integration test.
     *
     * @author Filip Hrisafov
     */
    enum ProcessorType {

        JAVAC( "javac" ),

        ECLIPSE_JDT( "jdt", JRE.JAVA_8 ),

        PROCESSOR_PLUGIN( null, JRE.JAVA_8 );

        private final String compilerId;
        private final JRE max;

        ProcessorType(String compilerId) {
            this( compilerId, JRE.OTHER );
        }

        ProcessorType(String compilerId, JRE max) {
            this.compilerId = compilerId;
            this.max = max;
        }

        public String getCompilerId() {
            return compilerId;
        }

        public JRE maxJre() {
            return max;
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
         * @param currentJreVersion the current JRE version
         *
         * @return additional command line arguments to be passed to the Maven {@link Verifier}.
         */
        Collection<String> getAdditionalCommandLineArguments(ProcessorType processorType,
            JRE currentJreVersion);
    }


    /**
     * @return a path in the classpath that contains the maven module to run as integration test: {@code mvn clean test}
     */
    String baseDir();

    /**
     * @return the variants to execute the integration tests with. See {@link ProcessorType}.
     */
    ProcessorType[] processorTypes() default {
        ProcessorType.JAVAC,
        ProcessorType.ECLIPSE_JDT,
        ProcessorType.PROCESSOR_PLUGIN
    };

    /**
     * @return the {@link CommandLineEnhancer} implementation. Must have a default constructor.
     */
    Class<? extends CommandLineEnhancer> commandLineEnhancer() default CommandLineEnhancer.class;

    boolean forkJvm() default false;

}
