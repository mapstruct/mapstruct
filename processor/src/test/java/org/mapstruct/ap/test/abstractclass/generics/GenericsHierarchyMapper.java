/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass.generics;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class GenericsHierarchyMapper {
    public static final GenericsHierarchyMapper INSTANCE = Mappers.getMapper( GenericsHierarchyMapper.class );

    @Mapping(target = "animalKey", source = "key")
    public abstract Target toTarget(AbstractAnimal source);

    @Mapping(target = "keyOfAllBeings", source = "key")
    public abstract Target toTarget(AbstractHuman source);

    @Mapping(target = "key", source = "animalKey")
    public abstract void updateSourceWithAnimalKey(Target target, @MappingTarget AbstractAnimal bean);

    @Mapping(target = "key", source = "keyOfAllBeings")
    public abstract void updateSourceWithKeyOfAllBeings(Target target, @MappingTarget AbstractHuman bean);

    protected AnimalKey modifyAnimalKey(AnimalKey item) {
        item.setTypeParameterIsResolvedToAnimalKey( true );
        return item;
    }

    protected KeyOfAllBeings modifyKeyOfAllBeings(KeyOfAllBeings item) {
        item.setTypeParameterIsResolvedToKeyOfAllBeings( true );
        return item;
    }
}
