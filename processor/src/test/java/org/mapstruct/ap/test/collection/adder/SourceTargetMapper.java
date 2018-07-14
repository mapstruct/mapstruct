/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
