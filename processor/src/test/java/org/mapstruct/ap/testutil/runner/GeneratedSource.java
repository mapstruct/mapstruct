/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mapstruct.ap.testutil.assertions.JavaFileAssert;

import static org.assertj.core.api.Assertions.fail;
import static org.mapstruct.ap.testutil.runner.CompilingExtension.NAMESPACE;

/**
 * A {@link org.junit.jupiter.api.extension.RegisterExtension RegisterExtension} to perform assertions on generated
 * source files.
 * <p>
 * To add it to the test, use:
 *
 * <pre>
 * &#064;RegisterExtension
 * final GeneratedSource generatedSources = new GeneratedSource();
 * </pre>
 *
 * @author Andreas Gudian
 */
public class GeneratedSource implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final String FIXTURES_ROOT = "fixtures/";

    /**
     * ThreadLocal, as the source dir must be injected for this extension to gain access
     * to the compilation information. As test execution of different classes in parallel is supported.
     */
    private ThreadLocal<String> sourceOutputDir = new ThreadLocal<>();

    private Compiler compiler;

    private List<Class<?>> fixturesFor = new ArrayList<>();

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        CompilationRequest compilationRequest = context.getStore( NAMESPACE )
            .get( context.getUniqueId() + "-compilationRequest", CompilationRequest.class );
        this.compiler = compilationRequest.getCompiler();
        setSourceOutputDir( context.getStore( NAMESPACE )
            .get( compilationRequest, CompilationCache.class )
            .getLastSourceOutputDir() );
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        handleFixtureComparison();
        clearSourceOutputDir();
    }

    private void setSourceOutputDir(String sourceOutputDir) {
        this.sourceOutputDir.set( sourceOutputDir );
    }

    private void clearSourceOutputDir() {
        this.sourceOutputDir.remove();
    }

    /**
     * Adds more mappers that need to be compared.
     *
     * The comparison is done for mappers and the are compared against a Java file that matches the name of the
     * Mapper that would have been created for the fixture.
     *
     * @param fixturesFor the classes that need to be compared with
     * @return the same rule for chaining
     */
    public GeneratedSource addComparisonToFixtureFor(Class<?>... fixturesFor) {
        this.fixturesFor.addAll( Arrays.asList( fixturesFor ) );
        return this;
    }

    /**
     * @param mapperClass the class annotated with {@code &#064;Mapper}
     *
     * @return an assert for the *Impl.java for the given mapper
     */
    public JavaFileAssert forMapper(Class<?> mapperClass) {
        String generatedJavaFileName = getMapperName( mapperClass );
        return forJavaFile( generatedJavaFileName );
    }

    private String getMapperName(Class<?> mapperClass) {
        return mapperClass.getName().replace( '.', '/' ).concat( "Impl.java" );
    }

    /**
     * @param mapperClass the class annotated with {@code &#064;Mapper} and {@code &#064;DecoratedWith(..)}
     *
     * @return an assert for the *Impl_.java for the given mapper
     */
    public JavaFileAssert forDecoratedMapper(Class<?> mapperClass) {
        String generatedJavaFileName = mapperClass.getName().replace( '.', '/' ).concat( "Impl_.java" );
        return forJavaFile( generatedJavaFileName );
    }

    /**
     * @param path the path relative to the source output directory of the java file to return an assert for
     *
     * @return an assert for the file specified by the given path
     */
    public JavaFileAssert forJavaFile(String path) {
        return new JavaFileAssert( new File( sourceOutputDir.get() + "/" + path ) );
    }

    private void handleFixtureComparison() throws UnsupportedEncodingException {
        for ( Class<?> fixture : fixturesFor ) {
            String fixtureName = getMapperName( fixture );
            URL expectedFile = getExpectedResource( fixtureName );
            if ( expectedFile == null ) {
                fail( String.format(
                    "No reference file could be found for Mapper %s. You should create a file %s",
                    fixture.getName(),
                    FIXTURES_ROOT + fixtureName
                ) );
            }
            else {
                File expectedResource = new File( URLDecoder.decode( expectedFile.getFile(), "UTF-8" ) );
                forMapper( fixture ).hasSameMapperContent( expectedResource );
            }
            fixture.getPackage().getName();
        }

    }

    private URL getExpectedResource( String fixtureName ) {
        ClassLoader classLoader = getClass().getClassLoader();
        for ( int version = Runtime.version().feature(); version >= 11 && compiler != Compiler.ECLIPSE; version-- ) {
            URL resource = classLoader.getResource( FIXTURES_ROOT + "/" + version + "/" + fixtureName );
            if ( resource != null ) {
                return resource;
            }
        }

        return classLoader.getResource( FIXTURES_ROOT + fixtureName );
    }
}
