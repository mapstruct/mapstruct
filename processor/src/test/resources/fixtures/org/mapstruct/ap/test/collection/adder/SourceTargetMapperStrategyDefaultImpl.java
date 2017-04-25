/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
