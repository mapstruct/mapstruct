/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingsource.primary;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingSource;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MappingSourcePrimaryMapper {

    MappingSourcePrimaryMapper INSTANCE = Mappers.getMapper( MappingSourcePrimaryMapper.class );

    Target mapWithPrimarySource(Source1 source1, @MappingSource(primary = true) Source2 source2);

    Target mapWithPrimaryMissingProperty(Source1 source1, @MappingSource(primary = true) Source3 source3);

    @Mapping(target = "name")
    Target mapExplicitTargetWithPrimary(Source1 source1, @MappingSource(primary = true) Source2 source2);

    class Source1 {
        private String name;
        private Integer value;

        public Source1(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    class Source2 {
        private String name;
        private Long count;

        public Source2(String name, Long count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }

    class Source3 {
        private Integer id;
        private Boolean active;

        public Source3(Integer id, Boolean active) {
            this.id = id;
            this.active = active;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }
    }

    class Target {
        private String name;
        private Integer value;
        private Long count;
        private Integer id;
        private Boolean active;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }
    }
}
