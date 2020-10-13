/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.workarounds;

import javax.annotation.processing.ProcessingEnvironment;

public class EclipseElementsDecorator extends AbstractElementsDecorator {

    EclipseElementsDecorator(ProcessingEnvironment processingEnv) {
        super( processingEnv );
    }
}
