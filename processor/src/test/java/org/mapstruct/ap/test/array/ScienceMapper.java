/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
