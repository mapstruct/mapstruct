/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import androidx.room.compiler.processing.XProcessingEnv;

import javax.annotation.processing.ProcessingEnvironment;

public class JavacTypeUtilsDecorator extends AbstractTypeUtilsDecorator {

    JavacTypeUtilsDecorator(XProcessingEnv processingEnv) {
        super( processingEnv );
    }
}
