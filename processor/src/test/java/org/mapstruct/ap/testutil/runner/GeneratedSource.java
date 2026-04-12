/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    private List<FixtureComparison> fixturesFor = new ArrayList<>();

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
        for ( Class<?> fixture : fixturesFor ) {
            this.fixturesFor.add( new FixtureComparison( fixture, null ) );
        }
        return this;
    }

    /**
     * Adds a mapper that needs to be compared against a fixture with a variant suffix.
     * <p>
     * The fixture is looked up at {@code fixtures/<FQN>Impl_{variant}.java} instead of the default
     * {@code fixtures/<FQN>Impl.java}. Use this to have multiple fixtures for the same mapper class
     * (e.g. for testing different compilation inputs that produce different generated code).
     *
     * @param fixtureFor the class to compare
     * @param variant the fixture variant (suffix appended to the file name before {@code .java})
     * @return the same rule for chaining
     */
    public GeneratedSource addComparisonToFixtureFor(Class<?> fixtureFor, String variant) {
        this.fixturesFor.add( new FixtureComparison( fixtureFor, variant ) );
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

    private void handleFixtureComparison() {
        for ( FixtureComparison fixture : fixturesFor ) {
            String fixtureName = getFixtureName( fixture.mapperClass, fixture.variant );
            URL expectedFile = getExpectedResource( fixtureName );
            if ( expectedFile == null ) {
                fail( String.format(
                    "No reference file could be found for Mapper %s. You should create a file %s",
                    fixture.mapperClass.getName(),
                    FIXTURES_ROOT + fixtureName
                ) );
            }
            else {
                File expectedResource = new File( URLDecoder.decode( expectedFile.getFile(), StandardCharsets.UTF_8 ) );
                forMapper( fixture.mapperClass ).hasSameMapperContent( expectedResource );
            }
        }
    }

    private String getFixtureName(Class<?> mapperClass, String variant) {
        String suffix = variant != null ? "Impl_" + variant + ".java" : "Impl.java";
        return mapperClass.getName().replace( '.', '/' ).concat( suffix );
    }

    private URL getExpectedResource(String fixtureName) {
        ClassLoader classLoader = getClass().getClassLoader();
        for ( int version = Runtime.version().feature(); version >= 11 && compiler != Compiler.ECLIPSE; version-- ) {
            URL resource = classLoader.getResource( FIXTURES_ROOT + "/" + version + "/" + fixtureName );
            if ( resource != null ) {
                return resource;
            }
        }

        return classLoader.getResource( FIXTURES_ROOT + fixtureName );
    }

    private static final class FixtureComparison {
        private final Class<?> mapperClass;
        private final String variant;

        FixtureComparison(Class<?> mapperClass, String variant) {
            this.mapperClass = mapperClass;
            this.variant = variant;
        }
    }
}
