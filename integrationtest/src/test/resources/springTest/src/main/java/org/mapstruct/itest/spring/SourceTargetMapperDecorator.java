/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class SourceTargetMapperDecorator implements DecoratedSourceTargetMapper {

    @Autowired
    @Qualifier( "delegate" )
    private DecoratedSourceTargetMapper delegate;

    @Override
    public Target sourceToTarget(Source source) {
        Target t = delegate.sourceToTarget( source );
        t.setFoo( 43L );
        return t;
    }
}
