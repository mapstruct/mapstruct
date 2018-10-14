/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedproperties.simple;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.nestedproperties.simple._target.TargetObject;
import org.mapstruct.ap.test.nestedproperties.simple.source.SourceRoot;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleMapper {

    SimpleMapper MAPPER = Mappers.getMapper( SimpleMapper.class );

    @Mapping( target = "longValue", source = "props.longValue" )
    @Mapping( target = "publicLongValue", source = "props.publicLongValue" )
    @Mapping( target = "intValue", source = "props.intValue" )
    @Mapping( target = "doubleValue", source = "props.doubleValue" )
    @Mapping( target = "floatValue", source = "props.floatValue" )
    @Mapping( target = "shortValue", source = "props.shortValue" )
    @Mapping( target = "charValue", source = "props.charValue" )
    @Mapping( target = "byteValue", source = "props.byteValue" )
    @Mapping( target = "booleanValue", source = "props.booleanValue" )
    @Mapping( target = "byteArray", source = "props.byteArray" )
    @Mapping( target = "stringValue", source = "props.stringValue" )
    TargetObject toTargetObject(SourceRoot sourceRoot);

    @InheritInverseConfiguration
    SourceRoot toSourceRoot(TargetObject targetObject);

}
