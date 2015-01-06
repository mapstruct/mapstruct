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
package org.mapstruct.ap.test.collection.map;

import java.util.Date;
import java.util.Map;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CustomNumberMapper.class)
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    Map<String, String> longDateMapToStringStringMap(Map<Long, Date> source);
    @InheritInverseConfiguration
    Map<Long, Date> stringStringMapToLongDateMap(Map<String, String> source);

    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    void stringStringMapToLongDateMapUsingTargetParameter(@MappingTarget Map<Long, Date> target,
                                                          Map<String, String> source);

    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    Map<Long, Date> stringStringMapToLongDateMapUsingTargetParameterAndReturn(Map<String, String> source,
                                                                              @MappingTarget Map<Long, Date> target);

    Target sourceToTarget(Source source);
    @InheritInverseConfiguration
    Source targetToSource(Target target);

    Map<Number, Number> intIntToNumberNumberMap(Map<Integer, Integer> source);
}
