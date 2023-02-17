/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3165;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface Issue3165Mapper {

    Issue3165Mapper INSTANCE = Mappers.getMapper( Issue3165Mapper.class );

    Target toTarget(Source source);

    class Source {
        private String[] pets;

        public Source(String[] pets) {
            this.pets = pets;
        }

        public String[] getPets() {
            return pets;
        }
    }

    class Target {
        private List<String> pets;

        Target() {
            this.pets = new ArrayList<>();
        }

        public List<String> getPets() {
            return pets;
        }

        public void addPet(String pet) {
            pets.add( pet );
        }
    }
}
