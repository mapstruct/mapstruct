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

import java.util.Arrays;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;

/**
 * A JUnit4 runner for Annotation Processor tests.
 * <p>
 * Test classes are safe to be executed in parallel, but test methods are not safe to be executed in parallel.
 * <p>
 * The classes to be compiled for a given test method must be specified via {@link WithClasses}. In addition the
 * following things can be configured optionally :
 * <ul>
 * <li>Processor options to be considered during compilation via {@link ProcessorOption}.</li>
 * <li>The expected compilation outcome and expected diagnostics can be specified via {@link ExpectedCompilationOutcome}
 * . If no outcome is specified, a successful compilation is assumed.</li>
 * </ul>
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public class AnnotationProcessorTestRunner extends ParentRunner<Runner> {
    private final List<Runner> runners;

    /**
     * @param klass the test class
     *
     * @throws Exception see {@link BlockJUnit4ClassRunner#BlockJUnit4ClassRunner(Class)}
     */
    public AnnotationProcessorTestRunner(Class<?> klass) throws Exception {
        super( klass );

        runners = Arrays.<Runner> asList(
            new InnerAnnotationProcessorRunner( klass, Compiler.JDK ),
            new InnerAnnotationProcessorRunner( klass, Compiler.ECLIPSE ) );
    }

    @Override
    protected List<Runner> getChildren() {
        return runners;
    }

    @Override
    protected Description describeChild(Runner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(Runner child, RunNotifier notifier) {
        child.run( notifier );
    }
}
