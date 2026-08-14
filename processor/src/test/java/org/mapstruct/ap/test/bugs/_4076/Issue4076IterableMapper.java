/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4076;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Covers the container mapping methods (iterable / map / stream) and update methods, which are built through
 * {@code ContainerMappingMethodBuilder} rather than {@code BeanMappingMethod.Builder}.
 */
@Mapper
public interface Issue4076IterableMapper {

    @Nullable
    List<String> mapList(@Nullable List<String> source);

    void update(@Nullable Source source, @MappingTarget Target target);

    class Source {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Target {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
