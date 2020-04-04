/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1159;

import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;

/**
 * @author Filip Hrisafov
 */
public class FaultyAstModifyingAnnotationProcessor implements AstModifyingAnnotationProcessor {

    public FaultyAstModifyingAnnotationProcessor() {
        throw new RuntimeException( "Faulty AstModifyingAnnotationProcessor should not be instantiated" );
    }

    @Override
    public boolean isTypeComplete(TypeMirror type) {
        return false;
    }
}
