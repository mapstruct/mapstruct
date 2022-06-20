/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.testutil;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface ProcessorTestConfiguration {
    Class<?>[] getAnnotationProcessorClasses();

    String[] getAnnotationProcessorPackagesOrClasses();

    String[] getAnnotationProcessorAndTestRootPackagesOrClasses();

    String getGenerateFileName(Class<?> sourceClass);

    static ProcessorTestConfiguration getConfiguration() {
        ServiceLoader<ProcessorTestConfiguration> serviceLoader =
            ServiceLoader.load( ProcessorTestConfiguration.class );
        Iterator<ProcessorTestConfiguration> configurations = serviceLoader.iterator();
        if ( configurations.hasNext() ) {
            return configurations.next();
        }
        throw new IllegalStateException(
                "ProcessorTestConfiguration is missing. "
                    + "Add a service implementation for org.mapstruct.testutil.ProcessorTestConfiguration." );
    }
}
