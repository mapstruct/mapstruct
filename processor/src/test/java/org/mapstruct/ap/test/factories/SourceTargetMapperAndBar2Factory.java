/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.factories.a.BarFactory;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper( uses = { BarFactory.class, org.mapstruct.ap.test.factories.b.BarFactory.class,
    org.mapstruct.ap.test.factories.c.BarFactory.class } )
public abstract class SourceTargetMapperAndBar2Factory {
    public static final SourceTargetMapperAndBar2Factory INSTANCE =
        Mappers.getMapper( SourceTargetMapperAndBar2Factory.class );

    public abstract Target sourceToTarget(Source source);

    public abstract Bar1 foo1ToBar1(Foo1 foo1);

    public abstract Bar2 foo2ToBar2(Foo2 foo2);

    public abstract Bar3 foo3ToBar3(Foo3 foo3);

    public abstract Bar4 foo4ToBar4(Foo4 foo4);

    public abstract CustomList<String> customListToList(List<String> list);

    public abstract CustomMap<String, String> customMapToMap(Map<String, String> list);

    public Bar2 createBar2() {
        return new Bar2( "BAR2" );
    }

    public CustomList<String> createCustomList() {
        return new CustomListImpl<>( "CUSTOMLIST" );
    }

    public CustomMap<String, String> createCustomMap() {
        return new CustomMapImpl<>( "CUSTOMMAP" );
    }
}
