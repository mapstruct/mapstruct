/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.type.TypeMirror;

/**
 *  @deprecated Superseded by {@link org.aptintegration.tools.spi.AstModifyingAnnotationProcessor}
 */
@Deprecated
public interface AstModifyingAnnotationProcessor extends org.aptintegration.tools.spi.AstModifyingAnnotationProcessor {

    @Override
    boolean isTypeComplete(TypeMirror var1);
}

