/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1966;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( imports = Collections.class )
public interface Issue1966Mapper {

    Issue1966Mapper INSTANCE = Mappers.getMapper( Issue1966Mapper.class );

    @Mapping(target = "previousNames",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        defaultExpression = "java(Collections.emptyList())")
    Animal toAnimal(AnimalRecord record);

    class AnimalRecord {

         private String[] previousNames;

        public String[] getPreviousNames() {
            return previousNames;
        }

        public void setPreviousNames(String[] previousNames) {
            this.previousNames = previousNames;
        }
    }

    class Animal {

        private List<String> previousNames;

        public List<String> getPreviousNames() {
            return previousNames;
        }

        public void setPreviousNames(List<String> previousNames) {
            this.previousNames = previousNames;
        }
    }

}
