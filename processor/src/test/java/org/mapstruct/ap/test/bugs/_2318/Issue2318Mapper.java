/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2318;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = Issue2318Mapper.Config.class)
public interface Issue2318Mapper {

    Issue2318Mapper INSTANCE = Mappers.getMapper( Issue2318Mapper.class );

    @MapperConfig
    interface Config {

        @Mapping(target = "parentValue1", source = "holder")
        Model.TargetParent mapParent(Model.SourceParent parent);

        @InheritConfiguration(name = "mapParent")
        @Mapping(target = "childValue", source = "value")
        @Mapping(target = "parentValue2", source = "holder.parentValue2")
        Model.TargetChild mapChild(Model.SourceChild child);
    }

    @InheritConfiguration(name = "mapChild")
    Model.TargetChild mapChild(Model.SourceChild child);

    default String parentValue1(Model.SourceParent.Holder holder) {
        return holder.getParentValue1();
    }
}
