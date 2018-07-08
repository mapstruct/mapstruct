/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.aptintegration;

import javax.lang.model.type.TypeMirror;

import org.aptintegration.tools.spi.AstModifyingAnnotationProcessor;


/**
 * @author Sjaak Derksen
 */
public class AlwaysIncomplete implements AstModifyingAnnotationProcessor {

    @Override
    public boolean isTypeComplete(TypeMirror var1) {
        return false;
    }
}
