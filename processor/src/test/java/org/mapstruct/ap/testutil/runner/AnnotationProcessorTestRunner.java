/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
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

    private static final boolean IS_AT_LEAST_JAVA_9 = isIsAtLeastJava9();

    private static boolean isIsAtLeastJava9() {
        try {
            Runtime.class.getMethod( "version" );
            return true;
        }
        catch ( NoSuchMethodException e ) {
            return false;
        }
    }

    private final List<Runner> runners;

    /**
     * @param klass the test class
     *
     * @throws Exception see {@link BlockJUnit4ClassRunner#BlockJUnit4ClassRunner(Class)}
     */
    public AnnotationProcessorTestRunner(Class<?> klass) throws Exception {
        super( klass );

        runners = createRunners( klass );
    }

    @SuppressWarnings("deprecation")
    private List<Runner> createRunners(Class<?> klass) throws Exception {
        WithSingleCompiler singleCompiler = klass.getAnnotation( WithSingleCompiler.class );

        if (singleCompiler != null) {
            return Arrays.asList( new InnerAnnotationProcessorRunner( klass, singleCompiler.value() ) );
        }
        else if ( IS_AT_LEAST_JAVA_9 ) {
            // Current tycho-compiler-jdt (0.26.0) is not compatible with Java 11
            // Updating to latest version 1.3.0 fails some tests
            // Once https://github.com/mapstruct/mapstruct/pull/1587 is resolved we can remove this line
            return Arrays.asList( new InnerAnnotationProcessorRunner( klass, Compiler.JDK11 ) );
        }

        return Arrays.asList(
            new InnerAnnotationProcessorRunner( klass, Compiler.JDK ),
            new InnerAnnotationProcessorRunner( klass, Compiler.ECLIPSE )
            );
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

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        super.filter( new FilterDecorator( filter ) );
    }

    /**
     * Allows to only execute selected methods, even if the executing framework is not aware of parameterized tests
     * (e.g. some versions of IntelliJ, Netbeans, Eclipse).
     */
    private static final class FilterDecorator extends Filter {
        private final Filter delegate;

        private FilterDecorator(Filter delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean shouldRun(Description description) {
            boolean shouldRun = delegate.shouldRun( description );
            if ( !shouldRun ) {
                return delegate.shouldRun( withoutParameterizedName( description ) );
            }

            return shouldRun;
        }

        @Override
        public String describe() {
            return delegate.describe();
        }

        private Description withoutParameterizedName(Description description) {
            String cleanDisplayName = removeParameter( description.getDisplayName() );
            Description cleanDescription =
                Description.createSuiteDescription(
                    cleanDisplayName,
                    description.getAnnotations().toArray( new Annotation[description.getAnnotations().size()] ) );

            for ( Description child : description.getChildren() ) {
                cleanDescription.addChild( withoutParameterizedName( child ) );
            }

            return cleanDescription;
        }

        private String removeParameter(String name) {
            if ( name.startsWith( "[" ) ) {
                return name;
            }

            // remove "[compiler]" from "method[compiler](class)"
            int open = name.indexOf( '[' );
            int close = name.indexOf( ']' ) + 1;
            return name.substring( 0, open ) + name.substring( close );
        }
    }
}
