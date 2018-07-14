/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.exceptions;

public abstract class ExceptionTestDecorator implements SourceTargetMapper {

    private final SourceTargetMapper delegate;

    public ExceptionTestDecorator(SourceTargetMapper delegate) {
        this.delegate = delegate;
    }
}
