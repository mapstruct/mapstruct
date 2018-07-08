/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.aptintegration;

import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;

/**
 * @author Sjaak Derksen
 */
public class AlwaysCompleteDeprecated implements AstModifyingAnnotationProcessor {

    @Override
    public boolean isTypeComplete(TypeMirror var1) {
        return true;
    }
}
