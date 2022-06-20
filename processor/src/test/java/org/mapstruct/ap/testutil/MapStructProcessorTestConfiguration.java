/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil;

import org.mapstruct.ap.MappingProcessor;
import org.mapstruct.testutil.ProcessorTestConfiguration;

public class MapStructProcessorTestConfiguration implements ProcessorTestConfiguration {

    @Override
    public Class<?>[] getAnnotationProcessorClasses() {
        return new Class<?>[] {
            MappingProcessor.class
        };
    }

}
