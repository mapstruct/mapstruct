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
package org.mapstruct.ap.test.bugs._1247;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1247Mapper {

    Issue1247Mapper INSTANCE = Mappers.getMapper( Issue1247Mapper.class );

    @Mappings( {
        @Mapping(target = "internal", source = "in"),
        @Mapping(target = "internal.internalData.list", source = "list")
    } )
    DtoOut map(DtoIn in, List<String> list);

    @Mappings( {
        @Mapping(target = "internal", source = "in"),
        @Mapping(target = "internal.expression", expression = "java(\"testingExpression\")"),
        @Mapping(target = "internal.internalData.list", source = "list"),
        @Mapping(target = "internal.internalData.defaultValue", source = "in.data2", defaultValue = "missing"),
        @Mapping(target = "constant", constant = "someConstant")
    } )
    OtherDtoOut mapWithConstantExpressionAndDefault(DtoIn in, List<String> list);

}
