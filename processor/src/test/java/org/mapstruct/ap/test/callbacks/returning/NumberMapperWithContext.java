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
package org.mapstruct.ap.test.callbacks.returning;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Pascal Grün
 */
@Mapper( uses = NumberMapperContext.class )
public abstract class NumberMapperWithContext {
    public static final NumberMapperWithContext INSTANCE = Mappers.getMapper( NumberMapperWithContext.class );

    public abstract Number integerToNumber(Integer number);

    public abstract void integerToNumber(Integer number, @MappingTarget Number target);

    public abstract Map<String, Integer> longMapToIntegerMap(Map<String, Long> target);

    public abstract List<String> setToList(Set<Integer> target);
}
