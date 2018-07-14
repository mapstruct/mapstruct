/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore.inherit;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ToolMapper {

    ToolMapper INSTANCE = Mappers.getMapper( ToolMapper.class );

    //
    @InheritConfiguration( name = "mapTool" )
    @BeanMapping( ignoreByDefault = true )
    HammerEntity mapHammer(HammerDto source);

    @Mapping( target = "type", source = "toolType" )
    @InheritConfiguration( name = "mapBase" )
    ToolEntity mapTool(ToolDto source);

    // demonstrates that all the businss stuff is mapped (implicit-by-name and defined)
    @InheritConfiguration( name = "mapBase" )
    @Mapping(target = "description", source = "articleDescription")
    WorkBenchEntity mapBench(WorkBenchDto source);

    // ignore all the base properties by default
    @BeanMapping( ignoreByDefault = true )
    BaseEntity mapBase(Object o);
}
