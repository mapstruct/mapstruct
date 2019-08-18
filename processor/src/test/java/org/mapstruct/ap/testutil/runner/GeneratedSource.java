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

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mapstruct.ap.testutil.assertions.JavaFileAssert;

import static org.assertj.core.api.Assertions.fail;

/**
 * A {@link TestRule} to perform assertions on generated source files.
 * <p>
 * To add it to the test, use:
 *
 * <pre>
 * &#064;Rule
 * public GeneratedSource generatedSources = new GeneratedSource();
 * </pre>
 *
 * @author Andreas Gudian
 */
public class GeneratedSource implements TestRule {

    private static final String FIXTURES_ROOT = "fixtures/";

    /**
     * static ThreadLocal, as the {@link CompilingStatement} must inject itself statically for this rule to gain access
     * to the statement's information. As test execution of different classes in parallel is supported.
     */
    private static ThreadLocal<CompilingStatement> compilingStatement = new ThreadLocal<>();

    private List<Class<?>> fixturesFor = new ArrayList<>();

    @Override
    public Statement apply(Statement base, Description description) {
        return new GeneratedSourceStatement( base );
    }

    static void setCompilingStatement(CompilingStatement compilingStatement) {
        GeneratedSource.compilingStatement.set( compilingStatement );
    }

    static void clearCompilingStatement() {
        GeneratedSource.compilingStatement.remove();
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
        return new JavaFileAssert( new File( compilingStatement.get().getSourceOutputDir() + "/" + path ) );
    }

    private class GeneratedSourceStatement extends Statement {
        private final Statement next;

        private GeneratedSourceStatement(Statement next) {
            this.next = next;
        }

        @Override
        public void evaluate() throws Throwable {
            next.evaluate();
            handleFixtureComparison();
        }
    }

    private void handleFixtureComparison() throws UnsupportedEncodingException {
        for ( Class<?> fixture : fixturesFor ) {
            String expectedFixture = FIXTURES_ROOT + getMapperName( fixture );
            URL expectedFile = getClass().getClassLoader().getResource( expectedFixture );
            if ( expectedFile == null ) {
                fail( String.format(
                    "No reference file could be found for Mapper %s. You should create a file %s",
                    fixture.getName(),
                    expectedFixture
                ) );
            }
            else {
                File expectedResource = new File( URLDecoder.decode( expectedFile.getFile(), "UTF-8" ) );
                forMapper( fixture ).hasSameMapperContent( expectedResource );
            }
            fixture.getPackage().getName();
        }

    }
}
