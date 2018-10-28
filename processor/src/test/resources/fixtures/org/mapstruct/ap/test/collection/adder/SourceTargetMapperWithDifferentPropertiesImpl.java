/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import javax.annotation.Generated;
import org.mapstruct.ap.test.collection.adder._target.TargetWithAnimals;
import org.mapstruct.ap.test.collection.adder.source.SourceWithPets;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-10-13T10:43:55+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
public class SourceTargetMapperWithDifferentPropertiesImpl implements SourceTargetMapperWithDifferentProperties {

    @Override
    public TargetWithAnimals map(SourceWithPets source) {
        if ( source == null ) {
            return null;
        }

        TargetWithAnimals targetWithAnimals = new TargetWithAnimals();

        if ( source.getPets() != null ) {
            for ( String pet : source.getPets() ) {
                targetWithAnimals.addAnimal( pet );
            }
        }

        return targetWithAnimals;
    }
}
