/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.io.IOException;

import org.junit.platform.launcher.LauncherInterceptor;

/**
 * @author hduelme
 */
public class CompilerLauncherInterceptor implements LauncherInterceptor {

    private ModifiableURLClassLoader newClassLoader;
    private ClassLoader orginalClassLoader;

    @Override
    public <T> T intercept(Invocation<T> invocation) {
        orginalClassLoader = Thread.currentThread().getContextClassLoader();
        if ( !orginalClassLoader.getClass().isAssignableFrom( ModifiableURLClassLoader.class ) ) {
            FilteringParentClassLoader filteringParentClassLoader = new FilteringParentClassLoader(
                    orginalClassLoader,
                    "org.mapstruct.ap.test."
            );
            newClassLoader = new ModifiableURLClassLoader( filteringParentClassLoader );
            newClassLoader.withOriginOf( getClass() );
            Thread.currentThread().setContextClassLoader( newClassLoader );
        }
        return invocation.proceed();
    }

    @Override
    public void close() {
        if ( newClassLoader != null ) {
            if ( newClassLoader == Thread.currentThread().getContextClassLoader() ) {
                Thread.currentThread().setContextClassLoader( orginalClassLoader );
            }

            try {
                newClassLoader.close();
            }
            catch ( IOException e ) {
                throw new RuntimeException( e );
            }
            newClassLoader = null;
        }
    }
}
