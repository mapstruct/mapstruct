/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import javax.annotation.Generated;
import org.mapstruct.ap.test.collection.adder._target.Target;
import org.mapstruct.ap.test.collection.adder.source.Source;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-04-09T23:05:40+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
public class SourceTargetMapperStrategyDefaultImpl implements SourceTargetMapperStrategyDefault {

    private final PetMapper petMapper = new PetMapper();

    @Override
    public Target shouldFallBackToAdder(Source source) throws DogException {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        try {
            target.setPets( petMapper.toPets( source.getPets() ) );
        }
        catch ( CatException e ) {
            throw new RuntimeException( e );
        }

        return target;
    }
}
