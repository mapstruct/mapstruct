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
package org.mapstruct.ap.test.array;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ap.test.array._target.ScientistDto;
import org.mapstruct.ap.test.array.source.Scientist;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScienceMapper {

    ScienceMapper INSTANCE = Mappers.getMapper( ScienceMapper.class );

    ScientistDto scientistToDto(Scientist scientist);

    ScientistDto[] scientistsToDtos(Scientist[] scientists);

    ScientistDto[] scientistsToDtos(List<Scientist> scientists);

    List<ScientistDto> scientistsToDtosAsList(Scientist[] scientists);

    ScientistDto[] scientistsToDtos(Scientist[] scientists, @MappingTarget ScientistDto[] target);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    boolean[] nvmMapping(boolean[] source);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    boolean[] nvmMapping(boolean[] source, @MappingTarget boolean[] target);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    short[] nvmMapping(int[] source, @MappingTarget short[] target);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    char[] nvmMapping(String[] source, @MappingTarget char[] target);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    int[] nvmMapping(int[] source, @MappingTarget int[] target);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    long[] nvmMapping(int[] source, @MappingTarget long[] target);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    float[] nvmMapping(int[] source, @MappingTarget float[] target);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    double[] nvmMapping(int[] source, @MappingTarget double[] target);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    String[] nvmMapping(int[] source, @MappingTarget String[] target);

    void nvmMappingVoidReturnNull(int[] source, @MappingTarget long[] target);

    @IterableMapping( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
    void nvmMappingVoidReturnDefault(int[] source, @MappingTarget long[] target);
}
