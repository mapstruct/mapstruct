/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import org.mapstruct.testutil.assertions.JavaFileAssert;

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
public class GeneratedSource extends org.mapstruct.testutil.runner.GeneratedSource {

    /**
     * @param mapperClass the class annotated with {@code &#064;Mapper}
     *
     * @return an assert for the *Impl.java for the given mapper
     */
    public JavaFileAssert forMapper(Class<?> mapperClass) {
        return forGeneratedFile( mapperClass );
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
     * {@inheritDoc}
     */
    @Override
    public GeneratedSource addComparisonToFixtureFor(Class<?>... fixturesFor) {
        return (GeneratedSource) super.addComparisonToFixtureFor( fixturesFor );
    }
}
