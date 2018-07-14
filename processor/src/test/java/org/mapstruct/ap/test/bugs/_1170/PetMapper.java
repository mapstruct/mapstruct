/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1170;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     *
     * @throws DogException
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
     *
     * @throws CatException
     * @throws DogException
     */
    public List<Long> toPets(List<? extends String> pets)  {
        List<Long> result = new ArrayList<Long>();
        for ( String pet : pets ) {
            result.add( toPet( pet ) );
        }
        return result;
    }

    public List<String> toSourcePets(List<Long> pets)  {
        List<String> result = new ArrayList<String>();
        for ( Long pet : pets ) {
            result.add( PETS_TO_SOURCE.get( pet ) );
        }
        return result;
    }

}
