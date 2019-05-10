/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.verbose;

import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;

public class AstModifyingAnnotationProcessorSaysNo implements AstModifyingAnnotationProcessor {

    @Override
    public boolean isTypeComplete(TypeMirror type) {
        return false;
    }
}
