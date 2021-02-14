/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.launcher.LauncherDiscoveryListener;

/**
 * @author Filip Hrisafov
 */
public class CompilerLauncherDiscoveryListener implements LauncherDiscoveryListener {
    @Override
    public void engineDiscoveryStarted(UniqueId engineId) {
        // Currently JUnit Jupiter does not have an SPI for providing a ClassLoader for loading the class
        // However, we can change the current context class loaded when the engine discovery starts.
        // This would make sure that JUnit Jupiter uses our ClassLoader to correctly load the mappers
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        FilteringParentClassLoader filteringParentClassLoader = new FilteringParentClassLoader(
            currentClassLoader,
            "org.mapstruct.ap.test."
        );
        ModifiableURLClassLoader newClassLoader = new ModifiableURLClassLoader( filteringParentClassLoader );
        newClassLoader.withOriginOf( getClass() );
        Thread.currentThread().setContextClassLoader( newClassLoader );
    }
}
