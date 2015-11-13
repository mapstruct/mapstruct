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
package org.mapstruct.ap.testutil.runner;

import java.io.File;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mapstruct.ap.testutil.assertions.JavaFileAssert;

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

    /**
     * static ThreadLocal, as the {@link CompilingStatement} must inject itself statically for this rule to gain access
     * to the statement's information. As test execution of different classes in parallel is supported.
     */
    private static ThreadLocal<CompilingStatement> compilingStatement = new ThreadLocal<CompilingStatement>();

    @Override
    public Statement apply(Statement base, Description description) {
        return base;
    }

    static void setCompilingStatement(CompilingStatement compilingStatement) {
        GeneratedSource.compilingStatement.set( compilingStatement );
    }

    static void clearCompilingStatement() {
        GeneratedSource.compilingStatement.remove();
    }

    /**
     * @param mapperClass the class annotated with {@code &#064;Mapper}
     *
     * @return an assert for the *Impl.java for the given mapper
     */
    public JavaFileAssert forMapper(Class<?> mapperClass) {
        String generatedJavaFileName = mapperClass.getName().replace( '.', '/' ).concat( "Impl.java" );
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
}
