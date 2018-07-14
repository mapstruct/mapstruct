/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.cdi;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public abstract class SourceTargetMapperDecorator implements DecoratedSourceTargetMapper {

    @Delegate
    @Inject
    private DecoratedSourceTargetMapper delegate;

    @Override
    public Target sourceToTarget(Source source) {
        Target t = delegate.sourceToTarget( source );
        t.setFoo( 43L );
        return t;
    }
}
