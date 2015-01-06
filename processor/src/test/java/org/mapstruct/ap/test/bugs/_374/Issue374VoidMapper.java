/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.bugs._374;

import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
public interface Issue374VoidMapper {

    Issue374VoidMapper INSTANCE = Mappers.getMapper( Issue374VoidMapper.class );

    @Mapping(target = "constant", constant = "test")
    void map(Source source, @MappingTarget Target target);

    void mapIterable(List<String> source, @MappingTarget List<String> target);

    void mapMap(Map<Integer, String> source, @MappingTarget Map<Integer, String> target);


}
