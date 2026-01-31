/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jsr330;

import jakarta.inject.Inject;
import jakarta.inject.Named;

public abstract class SourceTargetMapperDecorator implements DecoratedSourceTargetMapper {

    @Inject
    @Named("org.mapstruct.itest.jsr330.DecoratedSourceTargetMapperImpl_")
    private DecoratedSourceTargetMapper delegate;

    @Override
    public Target sourceToTarget(Source source) {
        Target t = delegate.sourceToTarget( source );
        t.setFoo( 43L );
        return t;
    }
}
