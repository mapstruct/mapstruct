/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.Arrays;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


/**
 * @author Sean Huang
 */
@Mapper
public abstract class SourceTargetMapper {

    public static final SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    //@Mapping( target = "someInteger2", source = "someNested.someInteger2" )
    abstract Target sourceToTarget(Source source);

    abstract void sourceToTarget(Source source, @MappingTarget Target target);

    @Mapping(target = "somePrimitiveDouble", defaultValue = "111.1")
    @Mapping(target = "someInteger", defaultValue = "222")
    @Mapping(target = "someList", defaultValue = "a,b")
    @Mapping(target = "someArray", defaultValue = "u,v")
    abstract Target sourceToTargetWitDefaults(Source source);

    protected List<String> toList( String in ) {
        return Arrays.asList( in.split( "," ) );
    }

    protected String[] toArray( String in ) {
        return in.split( "," );
    }
}
