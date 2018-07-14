/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousfactorymethod;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.a.BarFactory;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(uses = BarFactory.class)
public abstract class SourceTargetMapperAndBarFactory {
    public static final SourceTargetMapperAndBarFactory INSTANCE =
        Mappers.getMapper( SourceTargetMapperAndBarFactory.class );

    public abstract Target sourceToTarget(Source source);

    public abstract Bar fooToBar(Foo foo);

    public Bar createBar() {
        return new Bar( "BAR" );
    }
}
