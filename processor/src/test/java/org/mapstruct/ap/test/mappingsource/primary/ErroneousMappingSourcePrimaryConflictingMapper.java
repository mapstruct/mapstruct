/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingsource.primary;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingSource;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousMappingSourcePrimaryConflictingMapper {

    ErroneousMappingSourcePrimaryConflictingMapper INSTANCE = Mappers.getMapper(
        ErroneousMappingSourcePrimaryConflictingMapper.class );

    @Mapping(target = "value", ignore = true)
    Target mapFromMultiplePrimarySources(@MappingSource(primary = true) Source1 source1,
                                         @MappingSource(primary = true) Source2 source2);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "value")
    Target mapWithExplicitTargetMultiplePrimary(@MappingSource(primary = true) Source1 source1,
                                                @MappingSource(primary = true) Source2 source2);

    class Source1 {
        private String name;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Source2 {
        private String name;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Target {
        private String name;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
