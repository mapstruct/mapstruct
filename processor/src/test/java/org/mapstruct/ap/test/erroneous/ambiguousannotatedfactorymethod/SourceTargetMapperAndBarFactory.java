/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/**
 * @author Remo Meier
 */
@Mapper(uses = AmbiguousBarFactory.class)
public abstract class SourceTargetMapperAndBarFactory {
    public static final SourceTargetMapperAndBarFactory INSTANCE =
        Mappers.getMapper( SourceTargetMapperAndBarFactory.class );

    public abstract Target sourceToTarget(Source source);

    public abstract Bar fooToBar(Foo foo);

    @ObjectFactory
    public Bar createBar( Foo foo ) {
        return new Bar( "BAR" );
    }
}
