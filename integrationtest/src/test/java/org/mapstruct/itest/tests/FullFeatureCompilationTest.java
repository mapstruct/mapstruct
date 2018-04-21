/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
        ProcessorType.ORACLE_JAVA_6,
        ProcessorType.ORACLE_JAVA_7,
        ProcessorType.ORACLE_JAVA_8,
        ProcessorType.ORACLE_JAVA_9,
        ProcessorType.ECLIPSE_JDT_JAVA_6,
        ProcessorType.ECLIPSE_JDT_JAVA_7,
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

            switch ( processorType ) {
                case ORACLE_JAVA_6:
                    additionalExcludes.add( "org/mapstruct/ap/test/abstractclass/generics/*.java" );
                    additionalExcludes.add( "org/mapstruct/ap/test/bugs/_1170/*.java" );
                case ECLIPSE_JDT_JAVA_6:
                case ORACLE_JAVA_7:
                case ECLIPSE_JDT_JAVA_7:
                    additionalExcludes.add( "org/mapstruct/ap/test/bugs/_1425/*.java" );
                    additionalExcludes.add( "**/java8*/**/*.java" );
                    break;
                case ORACLE_JAVA_9:
                    // TODO find out why this fails:
                    additionalExcludes.add( "org/mapstruct/ap/test/collection/wildcard/BeanMapper.java" );
                    break;
                default:
            }

            Collection<String> result = new ArrayList<String>( additionalExcludes.size() );
            for ( int i = 0; i < additionalExcludes.size(); i++ ) {
                result.add( "-DadditionalExclude" + i + "=" + additionalExcludes.get( i ) );
            }

            return result;
        }
    }
}
