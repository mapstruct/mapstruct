/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.dagger2;

import javax.inject.Inject;
import javax.inject.Named;

public abstract class SecondSourceTargetMapperDecorator implements SecondDecoratedSourceTargetMapper {

    @Inject
    @Named("org.mapstruct.itest.dagger2.SecondDecoratedSourceTargetMapperImpl_")
    SecondDecoratedSourceTargetMapper delegate;

    @Override
    public Target sourceToTarget(Source source) {
        Target t = delegate.sourceToTarget( source );
        t.setFoo( 43L );
        return t;
    }
}
