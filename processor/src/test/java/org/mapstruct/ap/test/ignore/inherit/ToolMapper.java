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

    // demonstrates that all the business stuff is mapped only defined because BeanMapping#ignoreByDefault
    @InheritConfiguration( name = "mapBase" )
    @Mapping(target = "description", source = "articleDescription")
    WorkBenchEntity mapBench(WorkBenchDto source);

    // demonstrates that all the business stuff is mapped (implicit-by-name and defined)
    @BeanMapping( ignoreByDefault = false ) // needed to override the one from the BeanMapping mapBase
    @InheritConfiguration( name = "mapBase" )
    @Mapping(target = "description", source = "articleDescription")
    WorkBenchEntity mapBenchWithImplicit(WorkBenchDto source);

    // ignore all the base properties by default
    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "key", ignore = true)
    @Mapping( target = "modificationDate", ignore = true)
    @Mapping( target = "creationDate", ignore = true)
    BaseEntity mapBase(Object o);
}
