/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import javax.annotation.Generated;
import org.mapstruct.ap.test.collection.adder._target.TargetWithoutSetter;
import org.mapstruct.ap.test.collection.adder.source.Source;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:10:39+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class SourceTargetMapperStrategySetterPreferredImpl implements SourceTargetMapperStrategySetterPreferred {

    private final PetMapper petMapper = new PetMapper();

    @Override
    public TargetWithoutSetter toTargetDontUseAdder(Source source) throws DogException {
        if ( source == null ) {
            return null;
        }

        TargetWithoutSetter targetWithoutSetter = new TargetWithoutSetter();

        try {
            if ( source.getPets() != null ) {
                for ( String pet : source.getPets() ) {
                    targetWithoutSetter.addPet( petMapper.toPet( pet ) );
                }
            }
        }
        catch ( CatException e ) {
            throw new RuntimeException( e );
        }

        return targetWithoutSetter;
    }
}
