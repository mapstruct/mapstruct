/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4141;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue4141Mapper {

    Issue4141Mapper INSTANCE = Mappers.getMapper( Issue4141Mapper.class );

    void update(Source source, @MappingTarget Target target);

    class NestedSource {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Source {
        private Optional<NestedSource> nested = Optional.empty();

        public Optional<NestedSource> getNested() {
            return nested;
        }

        public void setNested(Optional<NestedSource> nested) {
            this.nested = nested;
        }
    }

    class NestedTarget {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Target {
        private NestedTarget nested;

        public NestedTarget getNested() {
            return nested;
        }

        public void setNested(NestedTarget nested) {
            this.nested = nested;
        }
    }
}
