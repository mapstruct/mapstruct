/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1170;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import com.google.common.collect.ImmutableMap;

/**
 * @author Cornelius Dirmeier
 */
@Mapper
public class PetMapper {

    private static final Map<String, Long> PETS_TO_TARGET = ImmutableMap.<String, Long>builder()
        .put( "rabbit", 1L )
        .put( "mouse", 2L ).build();

    private static final Map<Long, String> PETS_TO_SOURCE = ImmutableMap.<Long, String>builder()
        .put( 1L, "rabbit" )
        .put( 2L, "mouse" )
        .put( 3L, "cat" )
        .put( 4L, "dog" ).build();


    /**
     * method to be used when using an adder
     *
     * @param pet
     *
     * @return
     */
    public Long toPet(String pet)  {
        return PETS_TO_TARGET.get( pet );
    }

    public String toSourcePets(Long pet)  {
        return PETS_TO_SOURCE.get( pet );
    }

    /**
     * Method to be used when not using an adder
     *
     * @param pets
     *
     * @return
     */
    public List<Long> toPets(List<? extends String> pets)  {
        return pets.stream()
            .map( this::toPet )
            .collect( Collectors.toList() );
    }

    public List<String> toSourcePets(List<Long> pets)  {
        return pets.stream()
            .map( PETS_TO_SOURCE::get )
            .collect( Collectors.toList() );
    }

}
