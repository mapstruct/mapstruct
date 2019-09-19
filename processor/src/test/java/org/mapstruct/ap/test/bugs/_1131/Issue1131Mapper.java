/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1131;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public abstract class Issue1131Mapper {
    public static final Issue1131Mapper INSTANCE = Mappers.getMapper( Issue1131Mapper.class );

    public static final List<String> CALLED_METHODS = new ArrayList<>();

    public abstract void merge(Source source, @MappingTarget Target target);

    public abstract void mergeNested(List<Source.Nested> source, @MappingTarget List<Target.Nested> target);

    @ObjectFactory
    protected Target.Nested create(Source.Nested source) {
        CALLED_METHODS.add( "create(Source.Nested)" );
        return new Target.Nested( "from object factory" );
    }

    @ObjectFactory
    protected Target.Nested createWithSource(Source source) {
        throw new IllegalArgumentException( "Should not use create with source" );
    }

    @ObjectFactory
    protected List<Target.Nested> createWithSourceList(List<Source.Nested> source) {
        CALLED_METHODS.add( "create(List<Source.Nested>)" );
        List<Target.Nested> result = new ArrayList<>();
        result.add( new Target.Nested( "from createWithSourceList" ) );
        return result;
    }
}
