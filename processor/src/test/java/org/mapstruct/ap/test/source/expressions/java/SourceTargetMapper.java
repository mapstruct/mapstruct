/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.expressions.java;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.source.expressions.java.mapper.TimeAndFormat;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper( imports = TimeAndFormat.class )
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mappings( {
        @Mapping( target = "timeAndFormat", expression = "java( new TimeAndFormat( s.getTime(), s.getFormat() ))" ),
        @Mapping( target = "anotherProp", ignore = true )
    } )
    Target sourceToTarget( Source s );

    @Mappings( {
        @Mapping( target = "timeAndFormat",  expression = "java( new TimeAndFormat( s.getTime(), s.getFormat() ))"),
        @Mapping( target = "anotherProp", ignore = true )
    } )
    Target sourceToTargetWithMappingTarget(Source s, @MappingTarget Target t);
}
