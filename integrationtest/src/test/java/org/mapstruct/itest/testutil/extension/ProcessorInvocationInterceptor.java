/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.testutil.extension;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.maven.it.Verifier;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.platform.commons.util.ReflectionUtils;

import static org.apache.maven.it.util.ResourceExtractor.extractResourceToDestination;
import static org.apache.maven.shared.utils.io.FileUtils.copyURLToFile;
import static org.apache.maven.shared.utils.io.FileUtils.deleteDirectory;
import static org.mapstruct.itest.testutil.extension.ProcessorTestTemplateInvocationContext.CURRENT_VERSION;

/**
 * @author Filip Hrisafov
 * @author Andreas Gudian
 */
public class ProcessorInvocationInterceptor implements InvocationInterceptor {

    /**
     * System property to enable remote debugging of the processor execution in the integration test
     */
    public static final String SYS_PROP_DEBUG = "processorIntegrationTest.debug";

    private final ProcessorTestContext processorTestContext;

    public ProcessorInvocationInterceptor(ProcessorTestContext processorTestContext) {
        this.processorTestContext = processorTestContext;
    }

    @Override
    public void interceptTestTemplateMethod(Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        try {
            doExecute( extensionContext );
            invocation.proceed();
        }
        catch ( Exception e ) {
            invocation.skip();
            throw e;
        }
    }

    private void doExecute(ExtensionContext extensionContext) throws Exception {
        File destination = extractTest( extensionContext );
        PrintStream originalOut = System.out;

        final Verifier verifier;
        if ( Boolean.getBoolean( SYS_PROP_DEBUG ) ) {
            // the compiler is executed within the Maven JVM. So make
            // sure we fork a new JVM for that, and let that new JVM use the command 'mvnDebug' instead of 'mvn'
            verifier = new Verifier( destination.getCanonicalPath(), null, true, true );
            verifier.setDebugJvm( true );
        }
        else {
            verifier = new Verifier( destination.getCanonicalPath() );
            if ( processorTestContext.isForkJvm() ) {
                verifier.setForkJvm( true );
            }
        }

        List<String> goals = new ArrayList<>( 3 );

        goals.add( "clean" );

        try {
            configureProcessor( verifier );

            verifier.addCliOption( "-Dcompiler-source-target-version=" + sourceTargetVersion() );

            if ( Boolean.getBoolean( SYS_PROP_DEBUG ) ) {
                originalOut.print( "Processor Integration Test: " );
                originalOut.println( "Listening for transport dt_socket at address: 8000 (in some seconds)" );
            }

            goals.add( "test" );

            addAdditionalCliArguments( verifier );

            originalOut.println( extensionContext.getRequiredTestClass().getSimpleName() + "." +
                extensionContext.getRequiredTestMethod().getName() + " executing " +
                processorTestContext.getProcessor().name().toLowerCase() );

            verifier.executeGoals( goals );
            verifier.verifyErrorFreeLog();
        }
        finally {
            verifier.resetStreams();
        }
    }

    private void addAdditionalCliArguments(Verifier verifier)
        throws Exception {
        Class<? extends ProcessorTest.CommandLineEnhancer> cliEnhancerClass =
            processorTestContext.getCliEnhancerClass();

        Constructor<? extends ProcessorTest.CommandLineEnhancer> cliEnhancerConstructor = null;
        if ( cliEnhancerClass != ProcessorTest.CommandLineEnhancer.class ) {
            try {
                cliEnhancerConstructor = cliEnhancerClass.getConstructor();
                ProcessorTest.CommandLineEnhancer enhancer = cliEnhancerConstructor.newInstance();
                Collection<String> additionalArgs = enhancer.getAdditionalCommandLineArguments(
                    processorTestContext.getProcessor(), CURRENT_VERSION );

                for ( String arg : additionalArgs ) {
                    verifier.addCliOption( arg );
                }

            }
            catch ( NoSuchMethodException e ) {
                throw new RuntimeException( cliEnhancerClass + " does not have a default constructor." );
            }
            catch ( SecurityException e ) {
                throw new RuntimeException( e );
            }
        }
    }

    private void configureProcessor(Verifier verifier) {
        ProcessorTest.ProcessorType processor = processorTestContext.getProcessor();
        String compilerId = processor.getCompilerId();
        if ( compilerId != null ) {
            String profile = processor.getProfile();
            if ( profile == null ) {
                profile = "generate-via-compiler-plugin";
            }
            verifier.addCliOption( "-P" + profile );
            verifier.addCliOption( "-Dcompiler-id=" + compilerId );
            if ( processor == ProcessorTest.ProcessorType.JAVAC ) {
                if ( CURRENT_VERSION.ordinal() >= JRE.JAVA_21.ordinal() ) {
                    verifier.addCliOption( "-Dmaven.compiler.proc=full" );
                }
            }
        }
        else {
            verifier.addCliOption( "-Pgenerate-via-processor-plugin" );
        }
    }

    private File extractTest(ExtensionContext extensionContext) throws IOException {
        String tmpDir = getTmpDir();

        String tempDirName = extensionContext.getRequiredTestClass().getPackage().getName() + "." +
            extensionContext.getRequiredTestMethod().getName();
        File tempDirBase = new File( tmpDir, tempDirName ).getCanonicalFile();

        if ( !tempDirBase.exists() ) {
            tempDirBase.mkdirs();
        }

        File parentPom = new File( tempDirBase, "pom.xml" );
        copyURLToFile( getClass().getResource( "/pom.xml" ), parentPom );

        ProcessorTest.ProcessorType processorType = processorTestContext.getProcessor();
        File tempDir = new File( tempDirBase, processorType.name().toLowerCase() );
        deleteDirectory( tempDir );

        return extractResourceToDestination( getClass(), "/" + processorTestContext.getBaseDir(), tempDir, true );
    }

    private String getTmpDir() {
        if ( CURRENT_VERSION == JRE.JAVA_8 ) {
            // On Java 8 the tmp dir is always
            // no matter we run from the aggregator or not
            return "target/tmp";
        }

        // On Java 11+ we need to do it base on the location relative
        String tmpDir;
        if ( Files.exists( Paths.get( "integrationtest" ) ) ) {
            // If it exists then we are running from the main aggregator
            tmpDir = "integrationtest/target/tmp";
        }
        else {
            tmpDir = "target/tmp";
        }
        return tmpDir;
    }

    private String sourceTargetVersion() {
        if ( CURRENT_VERSION == JRE.JAVA_8 ) {
            return "1.8";
        }
        else if ( CURRENT_VERSION == JRE.OTHER ) {
            try {
                // Extracting the major version is done with code from
                // org.junit.jupiter.api.condition.JRE when determining the current version

                // java.lang.Runtime.version() is a static method available on Java 9+
                // that returns an instance of java.lang.Runtime.Version which has the
                // following method: public int major()
                Method versionMethod = null;
                versionMethod = Runtime.class.getMethod( "version" );
                Object version = ReflectionUtils.invokeMethod( versionMethod, null );
                Method majorMethod = version.getClass().getMethod( "major" );
                return String.valueOf( (int) ReflectionUtils.invokeMethod( majorMethod, version ) );
            }
            catch ( NoSuchMethodException e ) {
                throw new RuntimeException( "Failed to get Java Version" );
            }
        }
        else {
            return CURRENT_VERSION.name().substring( 5 );
        }
    }
}
