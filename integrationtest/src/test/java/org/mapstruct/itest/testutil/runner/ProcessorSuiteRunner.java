/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import static org.apache.maven.it.util.ResourceExtractor.extractResourceToDestination;
import static org.apache.maven.shared.utils.io.FileUtils.copyURLToFile;
import static org.apache.maven.shared.utils.io.FileUtils.deleteDirectory;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
import org.mapstruct.itest.testutil.runner.xml.Toolchains;
import org.mapstruct.itest.testutil.runner.xml.Toolchains.ProviderDescription;

/**
 * Runner for processor integration tests. Requires the annotation {@link ProcessorSuite} on the test class.
 *
 * @author Andreas Gudian
 */
public class ProcessorSuiteRunner extends ParentRunner<ProcessorTestCase> {

    /**
     * System property for specifying the location of the toolchains.xml file
     */
    public static final String SYS_PROP_TOOLCHAINS_FILE = "processorIntegrationTest.toolchainsFile";

    /**
     * System property to enable remote debugging of the processor execution in the integration test
     */
    public static final String SYS_PROP_DEBUG = "processorIntegrationTest.debug";

    private static final File TOOLCHAINS_FILE = getToolchainsFile();

    private static final Collection<ProcessorType> ENABLED_PROCESSOR_TYPES = detectSupportedTypes( TOOLCHAINS_FILE );

    public static final class ProcessorTestCase {
        private final String baseDir;
        private final ProcessorType processor;
        private final boolean ignored;

        public ProcessorTestCase(String baseDir, ProcessorType processor) {
            this.baseDir = baseDir;
            this.processor = processor;
            this.ignored = !ENABLED_PROCESSOR_TYPES.contains( processor );
        }
    }

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

        final Verifier verifier;
        if ( Boolean.getBoolean( SYS_PROP_DEBUG ) ) {
            if ( child.processor.getToolchain() == null ) {
                // when not using toolchains for a test, then the compiler is executed within the Maven JVM. So make
                // sure we fork a new JVM for that, and let that new JVM use the command 'mvnDebug' instead of 'mvn'
                verifier = new Verifier( destination.getCanonicalPath(), null, true, true );
                verifier.setDebugJvm( true );
            }
            else {
                verifier = new Verifier( destination.getCanonicalPath() );
                verifier.addCliOption( "-Pdebug-forked-javac" );
            }
        }
        else {
            verifier = new Verifier( destination.getCanonicalPath() );
        }

        List<String> goals = new ArrayList<String>( 3 );

        goals.add( "clean" );

        try {
            configureToolchains( child, verifier, goals, originalOut );
            configureProcessor( child, verifier );

            verifier.addCliOption( "-Dcompiler-source-target-version=" + child.processor.getSourceTargetVersion() );

            if ( "1.8".equals( child.processor.getSourceTargetVersion() )
                || "1.9".equals( child.processor.getSourceTargetVersion() ) ) {
                verifier.addCliOption( "-Dmapstruct-artifact-id=mapstruct-jdk8" );
            }
            else {
                verifier.addCliOption( "-Dmapstruct-artifact-id=mapstruct" );
            }

            if ( Boolean.getBoolean( SYS_PROP_DEBUG ) ) {
                originalOut.print( "Processor Integration Test: " );
                originalOut.println( "Listening for transport dt_socket at address: 8000 (in some seconds)" );
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
            verifier.addCliOption( "--toolchains" );
            verifier.addCliOption( TOOLCHAINS_FILE.getPath().replace( '\\', '/' ) );

            Toolchain toolchain = child.processor.getToolchain();

            verifier.addCliOption( "-Dtoolchain-jdk-vendor=" + toolchain.getVendor() );
            verifier.addCliOption( "-Dtoolchain-jdk-version=" + toolchain.getVersionRangeString() );

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

    private static File getToolchainsFile() {
        String specifiedToolchainsFile = System.getProperty( SYS_PROP_TOOLCHAINS_FILE );
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

        // use default location
        String defaultPath = System.getProperty( "user.home" ) + System.getProperty( "file.separator" ) + ".m2";
        return new File( defaultPath, "toolchains.xml" );
    }

    private static Collection<ProcessorType> detectSupportedTypes(File toolchainsFile) {
        Toolchains toolchains;
        try {
            if ( toolchainsFile.exists() ) {
                Unmarshaller unmarshaller = JAXBContext.newInstance( Toolchains.class ).createUnmarshaller();
                toolchains = (Toolchains) unmarshaller.unmarshal( toolchainsFile );
            }
            else {
                toolchains = null;
            }
        }
        catch ( JAXBException e ) {
            toolchains = null;
        }

        Collection<ProcessorType> supported = new HashSet<>();
        for ( ProcessorType type : ProcessorType.values() ) {
            if ( isSupported( type, toolchains ) ) {
                supported.add( type );
            }
        }

        return supported;
    }

    private static boolean isSupported(ProcessorType type, Toolchains toolchains) {
        if ( type.getToolchain() == null ) {
            return true;
        }

        if ( toolchains == null ) {
            return false;
        }

        Toolchain required = type.getToolchain();

        for ( Toolchains.Toolchain tc : toolchains.getToolchains() ) {
            if ( "jdk".equals( tc.getType() ) ) {
                ProviderDescription desc = tc.getProviderDescription();
                if ( desc != null
                    && required.getVendor().equals( desc.getVendor() )
                    && desc.getVersion() != null
                    && desc.getVersion().startsWith( required.getVersionMinInclusive() ) ) {
                    return true;
                }
            }
        }

        return false;
    }
}
