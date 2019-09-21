/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1904;

import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;

/**
 * @author Filip Hrisafov
 */
public class AstModifyingAnnotationProcessorSaysNo implements AstModifyingAnnotationProcessor {
    @Override
    public boolean isTypeComplete(TypeMirror type) {
        if ( type.toString().contains( "CarManualDto" ) ) {
            return false;
        }
        return true;
    }
}
