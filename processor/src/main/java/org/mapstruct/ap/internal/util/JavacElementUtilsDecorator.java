/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class JavacElementUtilsDecorator extends AbstractElementUtilsDecorator {

    JavacElementUtilsDecorator(ProcessingEnvironment processingEnv, TypeElement mapperElement) {
        super( processingEnv, mapperElement );
    }

    @Override
    protected TypeElement replaceTypeElementIfNecessary(TypeElement element) {
        return element;
    }
}
