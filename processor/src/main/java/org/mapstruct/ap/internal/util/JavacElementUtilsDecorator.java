/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.annotation.processing.ProcessingEnvironment;

public class JavacElementUtilsDecorator extends AbstractElementUtilsDecorator {

    JavacElementUtilsDecorator(ProcessingEnvironment processingEnv) {
        super( processingEnv );
    }
}
