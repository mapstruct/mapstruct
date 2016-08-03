/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.immutable;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Ciaran Liedeman
 */
@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    Target toTarget(Source source);

    @Mappings({
        @Mapping(target = "a")
    })
    Target toTargetSomeConstructorArgsMissing(Source source);

    @Mappings({
        @Mapping(target = "a"),
        @Mapping(target = "b", ignore = true)
    })
    Target toTargetSomeConstructorArgsIgnored(Source source);

    @Mappings({
            @Mapping(target = "a"),
            @Mapping(target = "b", constant = "20")
    })
    Target toTargetWithConstant(Source source);

    @Mappings({
            @Mapping(target = "a"),
            @Mapping(target = "b", defaultValue = "30"),
            @Mapping(target = "d", defaultValue = "40")
    })
    Target toTargetWithDefault(Source source);
}
