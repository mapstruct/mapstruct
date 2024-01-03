/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3495;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3495Mapper {

    Issue3495Mapper INSTANCE = Mappers.getMapper( Issue3495Mapper.class );

    @Mapping(target = "names", source = "names", conditionQualifiedByName = "alwaysFalse")
    void update(Source source, @MappingTarget TargetWithoutSetter target);

    @Condition
    @Named("alwaysFalse")
    default boolean alwaysFalse(@MappingTarget TargetWithoutSetter target) {
        return false;
    }

    class Source {

        private List<String> names;

        public List<String> getNames() {
            return names;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }
    }

    class TargetWithoutSetter {

        private final List<String> names = new ArrayList<>();

        public List<String> getNames() {
            return names;
        }

    }
}
