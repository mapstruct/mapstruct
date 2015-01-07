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
package org.mapstruct.ap.test.collection.adder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.mapstruct.TargetType;
import org.mapstruct.ap.test.collection.adder._target.IndoorPet;
import org.mapstruct.ap.test.collection.adder._target.OutdoorPet;
import org.mapstruct.ap.test.collection.adder._target.Pet;

/**
 * @author Sjaak Derksen
 */
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
     * @throws CatException
     * @throws DogException
     */
    public Long toPet(String pet) throws CatException, DogException {
        if ( "cat".equals( pet ) ) {
            throw new CatException();
        }
        else if ( "dog".equals( pet ) ) {
            throw new DogException();
        }
        return PETS_TO_TARGET.get( pet );
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
    public List<Long> toPets(List<String> pets) throws CatException, DogException {
        List<Long> result = new ArrayList<Long>();
        for ( String pet : pets ) {
            result.add( toPet( pet ) );
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T extends Pet> T toPet(String pet, @TargetType Class<T> clazz) throws CatException, DogException {
        if ( clazz == IndoorPet.class ) {
            return (T) new IndoorPet( toPet( pet ) );
        }
        if ( clazz == OutdoorPet.class ) {
            return (T) new OutdoorPet( toPet( pet ) );
        }
        return null;
    }

    public List<String> toSourcePets(List<Long> pets) throws CatException, DogException {
        List<String> result = new ArrayList<String>();
        for ( Long pet : pets ) {
            result.add( PETS_TO_SOURCE.get( pet ) );
        }
        return result;
    }
}
