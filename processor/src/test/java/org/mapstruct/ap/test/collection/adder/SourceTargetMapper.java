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

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.collection.adder._target.Target;
import org.mapstruct.ap.test.collection.adder._target.TargetDali;
import org.mapstruct.ap.test.collection.adder._target.TargetHuman;
import org.mapstruct.ap.test.collection.adder._target.TargetOnlyGetter;
import org.mapstruct.ap.test.collection.adder._target.TargetViaTargetType;
import org.mapstruct.ap.test.collection.adder.source.SingleElementSource;
import org.mapstruct.ap.test.collection.adder.source.Source;
import org.mapstruct.ap.test.collection.adder.source.SourceTeeth;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    uses = { PetMapper.class, TeethMapper.class })
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    Target toTarget(Source source) throws DogException;

    Source toSource(Target source);

    void toExistingTarget(Source source, @MappingTarget Target target);

    TargetDali toTargetDali(SourceTeeth source);

    TargetHuman toTargetHuman(SourceTeeth source);

    TargetOnlyGetter toTargetOnlyGetter(Source source) throws DogException;

    TargetViaTargetType toTargetViaTargetType(Source source);

    @Mapping(source = "pet", target = "pets")
    Target fromSingleElementSource(SingleElementSource source);
}
