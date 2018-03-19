/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
