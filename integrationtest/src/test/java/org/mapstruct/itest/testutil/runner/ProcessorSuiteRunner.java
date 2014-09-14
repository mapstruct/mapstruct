/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.itest.testutil.runner;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.it.Verifier;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.mapstruct.itest.testutil.runner.ProcessorSuite.ProcessorType;
import org.mapstruct.itest.testutil.runner.ProcessorSuiteRunner.ProcessorTestCase;

import static org.apache.maven.it.util.ResourceExtractor.extractResourceToDestination;
import static org.apache.maven.shared.utils.io.FileUtils.copyURLToFile;
import static org.apache.maven.shared.utils.io.FileUtils.deleteDirectory;

/**
 * Runner for processor integration tests. Requires the annotation {@link ProcessorSuite} on the test class.
 *
 * @author Andreas Gudian
 */
public class ProcessorSuiteRunner extends ParentRunner<ProcessorTestCase> {

    public static final class ProcessorTestCase {
        private final String baseDir;
        private final ProcessorType processor;
        private final boolean ignored;

        public ProcessorTestCase(String baseDir, ProcessorType processor) {
            this.baseDir = baseDir;
            this.processor = processor;
            this.ignored = !TOOLCHAINS_ENABLED && processor.getToolchain() != null;
        }
    }

    private static final File SPECIFIED_TOOLCHAINS_FILE = getSpecifiedToolchainsFile();
    private static final boolean TOOLCHAINS_ENABLED = toolchainsFileExists();

    private final List<ProcessorTestCase> methods;

    /**
     * @param clazz the test class
     * @throws InitializationError in case the initialization fails
     */
    public ProcessorSuiteRunner(Class<?> clazz) throws InitializationError {
        super( clazz );

        ProcessorSuite suite = clazz.getAnnotation( ProcessorSuite.class );

        if ( null == suite ) {
            throw new InitializationError( "The test class must be annotated with " + ProcessorSuite.class.getName() );
        }

        if ( suite.processorTypes().length == 0 ) {
            throw new InitializationError( "ProcessorSuite#processorTypes must not be empty" );
        }

        methods = initializeTestCases( suite );
    }

    private List<ProcessorTestCase> initializeTestCases(ProcessorSuite suite) {
        List<ProcessorType> types = new ArrayList<ProcessorType>();

        for ( ProcessorType compiler : suite.processorTypes() ) {
            if ( compiler.getIncluded().length > 0 ) {
                types.addAll( Arrays.asList( compiler.getIncluded() ) );
            }
            else {
                types.add( compiler );
            }
        }

        List<ProcessorTestCase> result = new ArrayList<ProcessorTestCase>( types.size() );

        for ( ProcessorType type : types ) {
            result.add( new ProcessorTestCase( suite.baseDir(), type ) );
        }

        return result;
    }

    @Override
    protected List<ProcessorTestCase> getChildren() {
        return methods;
    }

    @Override
    protected Description describeChild(ProcessorTestCase child) {
        return Description.createTestDescription( getTestClass().getJavaClass(), child.processor.name().toLowerCase() );
    }

    @Override
    protected void runChild(ProcessorTestCase child, RunNotifier notifier) {
        Description description = describeChild( child );
        EachTestNotifier testNotifier = new EachTestNotifier( notifier, description );

        if ( child.ignored ) {
            testNotifier.fireTestIgnored();
        }
        else {
            try {
                testNotifier.fireTestStarted();
                doExecute( child, description );
            }
            catch ( AssumptionViolatedException e ) {
                testNotifier.fireTestIgnored();
            }
            catch ( StoppedByUserException e ) {
                throw e;
            }
            catch ( Throwable e ) {
                testNotifier.addFailure( e );
            }
            finally {
                testNotifier.fireTestFinished();
            }
        }
    }

    private void doExecute(ProcessorTestCase child, Description description) throws Exception {
        File destination = extractTest( child, description );
        PrintStream originalOut = System.out;

        Verifier verifier = new Verifier( destination.getCanonicalPath() );

        List<String> goals = new ArrayList<String>( 3 );

        goals.add( "clean" );

        try {
            configureToolchains( child, verifier, goals, originalOut );
            configureProcessor( child, verifier );

            verifier.addCliOption( "-Dcompiler-source-target-version=" + child.processor.getSourceTargetVersion() );

            if ( "1.8".equals( child.processor.getSourceTargetVersion() ) ) {
                verifier.addCliOption( "-Dmapstruct-artifact-id=mapstruct-jdk8" );
            }
            else {
                verifier.addCliOption( "-Dmapstruct-artifact-id=mapstruct" );
            }

            goals.add( "test" );

            originalOut.println( "executing " + child.processor.name().toLowerCase() );

            verifier.executeGoals( goals );
            verifier.verifyErrorFreeLog();
        }
        finally {
            verifier.resetStreams();
        }
    }

    private void configureProcessor(ProcessorTestCase child, Verifier verifier) {
        if ( child.processor.getCompilerId() != null ) {
            verifier.addCliOption( "-Pgenerate-via-compiler-plugin" );
            verifier.addCliOption( "-Dcompiler-id=" + child.processor.getCompilerId() );
        }
        else {
            verifier.addCliOption( "-Pgenerate-via-processor-plugin" );
        }
    }

    private void configureToolchains(ProcessorTestCase child, Verifier verifier, List<String> goals,
                                     PrintStream originalOut) {
        if ( child.processor.getToolchain() != null ) {
            if ( null != SPECIFIED_TOOLCHAINS_FILE ) {
                verifier.addCliOption( "--toolchains" );
                verifier.addCliOption( SPECIFIED_TOOLCHAINS_FILE.getPath().replace( '\\', '/' ) );
            }

            String[] parts = child.processor.getToolchain().split( "-" );

            verifier.addCliOption( "-Dtoolchain-jdk-vendor=" + parts[0] );
            verifier.addCliOption( "-Dtoolchain-jdk-version=" + parts[1] );

            goals.add( "toolchains:toolchain" );
        }
    }

    private File extractTest(ProcessorTestCase child, Description description) throws IOException {
        File tempDirBase = new File( "target/tmp", description.getClassName() ).getCanonicalFile();

        if ( !tempDirBase.exists() ) {
            tempDirBase.mkdirs();
        }

        File parentPom = new File( tempDirBase, "pom.xml" );
        copyURLToFile( getClass().getResource( "/pom.xml" ), parentPom );

        File tempDir = new File( tempDirBase, description.getMethodName() );
        deleteDirectory( tempDir );

        return extractResourceToDestination( getClass(), "/" + child.baseDir, tempDir, true );
    }

    private static File getSpecifiedToolchainsFile() {
        String specifiedToolchainsFile = System.getProperty( "processorIntegrationTest.toolchainsFile" );
        if ( null != specifiedToolchainsFile ) {
            try {
                File canonical = new File( specifiedToolchainsFile ).getCanonicalFile();
                if ( canonical.exists() ) {
                    return canonical;
                }

                // check the path relative to the parent directory (allows specifying a path relative to the top-level
                // aggregator module)
                canonical = new File( "..", specifiedToolchainsFile ).getCanonicalFile();
                if ( canonical.exists() ) {
                    return canonical;
                }
            }
            catch ( IOException e ) {
                return null;
            }
        }

        return null;
    }

    private static boolean toolchainsFileExists() {
        if ( null != SPECIFIED_TOOLCHAINS_FILE ) {
            return SPECIFIED_TOOLCHAINS_FILE.exists();
        }

        String defaultPath = System.getProperty( "user.home" ) + System.getProperty( "file.separator" ) + ".m2";
        return new File( defaultPath, "toolchains.xml" ).exists();
    }

}
