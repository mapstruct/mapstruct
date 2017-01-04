/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.ap.test.collection.adder._target.IndoorPet;
import org.mapstruct.ap.test.collection.adder._target.Target;
import org.mapstruct.ap.test.collection.adder._target.TargetDali;
import org.mapstruct.ap.test.collection.adder._target.TargetHuman;
import org.mapstruct.ap.test.collection.adder._target.TargetOnlyGetter;
import org.mapstruct.ap.test.collection.adder._target.TargetViaTargetType;
import org.mapstruct.ap.test.collection.adder.source.SingleElementSource;
import org.mapstruct.ap.test.collection.adder.source.Source;
import org.mapstruct.ap.test.collection.adder.source.SourceTeeth;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:10:39+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    private final PetMapper petMapper = new PetMapper();
    private final TeethMapper teethMapper = new TeethMapper();

    @Override
    public Target toTarget(Source source) throws DogException {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getPets() != null ) {
            try {
                for ( String pet : source.getPets() ) {
                    target.addPet( petMapper.toPet( pet ) );
                }
            }
            catch ( CatException e ) {
                throw new RuntimeException( e );
            }
        }

        return target;
    }

    @Override
    public Source toSource(Target source) {
        if ( source == null ) {
            return null;
        }

        Source source_ = new Source();

        try {
            List<String> list = petMapper.toSourcePets( source.getPets() );
            if ( list != null ) {
                source_.setPets( list );
            }
        }
        catch ( CatException e ) {
            throw new RuntimeException( e );
        }
        catch ( DogException e ) {
            throw new RuntimeException( e );
        }

        return source_;
    }

    @Override
    public void toExistingTarget(Source source, Target target) {
        if ( source == null ) {
            return;
        }

        if ( source.getPets() != null ) {
            try {
                for ( String pet : source.getPets() ) {
                    target.addPet( petMapper.toPet( pet ) );
                }
            }
            catch ( CatException e ) {
                throw new RuntimeException( e );
            }
            catch ( DogException e ) {
                throw new RuntimeException( e );
            }
        }
    }

    @Override
    public TargetDali toTargetDali(SourceTeeth source) {
        if ( source == null ) {
            return null;
        }

        TargetDali targetDali = new TargetDali();

        if ( source.getTeeth() != null ) {
            for ( String teeth : source.getTeeth() ) {
                targetDali.addTeeth( teethMapper.toTooth( teeth ) );
            }
        }

        return targetDali;
    }

    @Override
    public TargetHuman toTargetHuman(SourceTeeth source) {
        if ( source == null ) {
            return null;
        }

        TargetHuman targetHuman = new TargetHuman();

        if ( source.getTeeth() != null ) {
            for ( String tooth : source.getTeeth() ) {
                targetHuman.addTooth( teethMapper.toTooth( tooth ) );
            }
        }

        return targetHuman;
    }

    @Override
    public TargetOnlyGetter toTargetOnlyGetter(Source source) throws DogException {
        if ( source == null ) {
            return null;
        }

        TargetOnlyGetter targetOnlyGetter = new TargetOnlyGetter();

        if ( source.getPets() != null ) {
            try {
                for ( String pet : source.getPets() ) {
                    targetOnlyGetter.addPet( petMapper.toPet( pet ) );
                }
            }
            catch ( CatException e ) {
                throw new RuntimeException( e );
            }
        }

        return targetOnlyGetter;
    }

    @Override
    public TargetViaTargetType toTargetViaTargetType(Source source) {
        if ( source == null ) {
            return null;
        }

        TargetViaTargetType targetViaTargetType = new TargetViaTargetType();

        if ( source.getPets() != null ) {
            try {
                for ( String pet : source.getPets() ) {
                    targetViaTargetType.addPet( petMapper.toPet( pet, IndoorPet.class ) );
                }
            }
            catch ( CatException e ) {
                throw new RuntimeException( e );
            }
            catch ( DogException e ) {
                throw new RuntimeException( e );
            }
        }

        return targetViaTargetType;
    }

    @Override
    public Target fromSingleElementSource(SingleElementSource source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getPet() != null ) {
            try {
                target.addPet( petMapper.toPet( source.getPet() ) );
            }
            catch ( CatException e ) {
                throw new RuntimeException( e );
            }
            catch ( DogException e ) {
                throw new RuntimeException( e );
            }
        }

        return target;
    }
}
