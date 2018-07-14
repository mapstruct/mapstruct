/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._603;

public abstract class SourceTargetMapperDecorator implements SourceTargetMapper {

    private final SourceTargetMapper delegate;

    protected SourceTargetMapperDecorator(SourceTargetMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public Target mapSourceToTarget(Source source) {
        return delegate.mapSourceToTarget( source );
    }
}
