/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator;

import org.mapstruct.AnnotateWith;

@AnnotateWith(value = TestAnnotation.class, elements = @AnnotateWith.Element(strings = "decoratorValue"))
public abstract class AnnotatedMapperDecorator implements AnnotatedMapper {

    private final AnnotatedMapper delegate;

    public AnnotatedMapperDecorator(AnnotatedMapper delegate) {
        this.delegate = delegate;
    }
}
