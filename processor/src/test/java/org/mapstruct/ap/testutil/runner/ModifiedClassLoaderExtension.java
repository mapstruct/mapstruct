/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.util.CollectionUtils;

import static org.mapstruct.ap.testutil.runner.CompilingExtension.NAMESPACE;

// CHECKSTYLE:OFF
/**
 * Special extension which is responsible for making sure that the tests are run with the
 * {@link ModifiableURLClassLoader}.
 * Otherwise, methods and classes might not be properly shared.
 * <p>
 * It intercepts all methods and if the test class was not loaded with the {@link ModifiableURLClassLoader}
 * then registers a selector for the test case to be run once the test is done.
 * The run is done by setting the Thread Context ClassLoader and manually invoking the {@link Launcher}
 * for the needed tests and test templates.
 * In order to be able to reuse the compilation caching we are running all tests once the current Class Extension
 * Context is closed.
 * <p>
 * This mechanism is needed since there is no way to register a custom ClassLoader for creating the test instance
 * in JUnit Jupiter (see <a href="https://github.com/junit-team/junit5/issues/201">junit-test/junit5#201</a>
 * for more information). Once there is support for registering a custom class loader we can simplify this.
 * <p>
 * This logic was heavily inspired and is really similar to the Spring Boot
 * <a href="https://github.com/spring-projects/spring-boot/blob/bde7bd0a1a310f48fb877b9a0d4a05b8d829d6c0/spring-boot-project/spring-boot-tools/spring-boot-test-support/src/main/java/org/springframework/boot/testsupport/classpath/ModifiedClassPathExtension.java">ModifiedClassPathExtension</a>.
 *
 * @author Filip Hrisafov
 * @see ModifiableURLClassLoader
 */
// CHECKSTYLE:ON
public class ModifiedClassLoaderExtension implements InvocationInterceptor {

    @Override
    public void interceptBeforeAllMethod(Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        intercept( invocation, extensionContext );
    }

    @Override
    public void interceptBeforeEachMethod(Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        intercept( invocation, extensionContext );
    }

    @Override
    public void interceptAfterEachMethod(Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        intercept( invocation, extensionContext );
    }

    @Override
    public void interceptAfterAllMethod(Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        intercept( invocation, extensionContext );
    }

    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
        ExtensionContext extensionContext) throws Throwable {
        if ( isModifiedClassPathClassLoader( extensionContext ) ) {
            invocation.proceed();
            return;
        }
        invocation.skip();
        // For normal Tests the path to the Class Store is:
        // method -> class
        // This will most likely never be the case for a processor test.
        ExtensionContext.Store store = extensionContext.getParent()
            .orElseThrow( () -> new IllegalStateException( extensionContext + " has no parent store " ) )
            .getStore( NAMESPACE );
        registerSelector( extensionContext, store );
    }

    @Override
    public void interceptTestTemplateMethod(Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        if ( isModifiedClassPathClassLoader( extensionContext ) ) {
            invocation.proceed();
            return;
        }
        invocation.skip();
        // For TestTemplates the path to the Class Store is:
        // method -> testTemplate -> class
        ExtensionContext.Store store = extensionContext.getParent()
            .flatMap( ExtensionContext::getParent )
            .orElseThrow( () -> new IllegalStateException( extensionContext + " has no parent store " ) )
            .getStore( NAMESPACE );
        registerSelector( extensionContext, store );
    }

    private void registerSelector(ExtensionContext context, ExtensionContext.Store store) {
        store.getOrComputeIfAbsent(
            context.getRequiredTestClass() + "-discoverySelectors",
            s -> new SelectorsToRun( context.getRequiredTestClass() ),
            SelectorsToRun.class
        ).discoverySelectors.add( DiscoverySelectors.selectUniqueId( context.getUniqueId() ) );
    }

    private static void runTestWithModifiedClassPath(Class<?> testClass, List<? extends DiscoverySelector> selectors)
        throws Throwable {
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        URLClassLoader modifiedClassLoader =
            new ModifiableURLClassLoader().withOriginOf( testClass );
        Thread.currentThread().setContextClassLoader( modifiedClassLoader );
        try {
            runTest( selectors );
        }
        finally {
            Thread.currentThread().setContextClassLoader( originalClassLoader );
        }
    }

    private static void runTest(List<? extends DiscoverySelector> selectors) throws Throwable {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors( selectors )
            .build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover( request );
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners( listener );
        launcher.execute( testPlan );
        TestExecutionSummary summary = listener.getSummary();
        if ( !CollectionUtils.isEmpty( summary.getFailures() ) ) {
            throw summary.getFailures().get( 0 ).getException();
        }
    }

    private void intercept(Invocation<Void> invocation, ExtensionContext extensionContext) throws Throwable {
        if ( isModifiedClassPathClassLoader( extensionContext ) ) {
            invocation.proceed();
            return;
        }
        invocation.skip();
    }

    static boolean isModifiedClassPathClassLoader(ExtensionContext extensionContext) {
        Class<?> testClass = extensionContext.getRequiredTestClass();
        ClassLoader classLoader = testClass.getClassLoader();
        return classLoader.getClass().getName().equals( ModifiableURLClassLoader.class.getName() );
    }

    static class SelectorsToRun implements ExtensionContext.Store.CloseableResource {

        private final Class<?> testClass;
        private final List<DiscoverySelector> discoverySelectors = new ArrayList<>();

        SelectorsToRun(Class<?> testClass) {
            this.testClass = testClass;
        }

        @Override
        public void close() throws Throwable {
            runTestWithModifiedClassPath( testClass, discoverySelectors );
        }
    }
}
