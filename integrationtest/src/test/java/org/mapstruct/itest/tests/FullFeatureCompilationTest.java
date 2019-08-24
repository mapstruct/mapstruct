/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.mapstruct.itest.tests.FullFeatureCompilationTest.CompilationExclusionCliEnhancer;
import org.mapstruct.itest.testutil.runner.ProcessorSuite;
import org.mapstruct.itest.testutil.runner.ProcessorSuite.CommandLineEnhancer;
import org.mapstruct.itest.testutil.runner.ProcessorSuite.ProcessorType;
import org.mapstruct.itest.testutil.runner.ProcessorSuiteRunner;

/**
 * Integration test that compiles all test mappers in the processor-module, excluding all classes that contain one of
 * the following in their path/file name:
 * <ul>
 * <li>{@code /erronerous/}</li>
 * <li>{@code *Erroneous*}</li>
 * <li>{@code *Test.java}</li>
 * <li>{@code /testutil/}</li>
 * <li>possibly more, depending on the processor type - see {@link CompilationExclusionCliEnhancer}</li>
 * </ul>
 *
 * @author Andreas Gudian
 */
@RunWith(ProcessorSuiteRunner.class)
@ProcessorSuite(
    baseDir = "fullFeatureTest",
    commandLineEnhancer = CompilationExclusionCliEnhancer.class,
    processorTypes = {
        ProcessorType.ORACLE_JAVA_8,
        ProcessorType.ORACLE_JAVA_9,
        ProcessorType.ECLIPSE_JDT_JAVA_8
})
public class FullFeatureCompilationTest {
    /**
     * Adds explicit exclusions of test mappers that are known or expected to not work with specific compilers.
     *
     * @author Andreas Gudian
     */
    public static final class CompilationExclusionCliEnhancer implements CommandLineEnhancer {
        @Override
        public Collection<String> getAdditionalCommandLineArguments(ProcessorType processorType) {
            List<String> additionalExcludes = new ArrayList<>();

            // SPI not working correctly here.. (not picked up)
            additionalExcludes.add( "org/mapstruct/ap/test/bugs/_1596/*.java" );

            switch ( processorType ) {
                case ORACLE_JAVA_9:
                    // TODO find out why this fails:
                    additionalExcludes.add( "org/mapstruct/ap/test/collection/wildcard/BeanMapper.java" );
                    break;
                default:
            }

            Collection<String> result = new ArrayList<>( additionalExcludes.size() );
            for ( int i = 0; i < additionalExcludes.size(); i++ ) {
                result.add( "-DadditionalExclude" + i + "=" + additionalExcludes.get( i ) );
            }

            return result;
        }
    }
}
