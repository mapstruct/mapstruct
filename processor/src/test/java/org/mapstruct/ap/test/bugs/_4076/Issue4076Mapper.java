/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4076;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface Issue4076Mapper {

    @Mapping( target = "name", source = "source1.name" )
    @Mapping( target = "description", source = "source2.description" )
    Target map(Source1 source1, @Nullable Source2 source2);

    @Mapping( target = "description", ignore = true )
    @Nullable
    Target mapNullableReturn(Source1 source1);

    class Source1 {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Source2 {

        private @Nullable String description;

        public @Nullable String getDescription() {
            return description;
        }

        public void setDescription(@Nullable String description) {
            this.description = description;
        }
    }

    class Target {

        private String name;

        private @Nullable String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public @Nullable String getDescription() {
            return description;
        }

        public void setDescription(@Nullable String description) {
            this.description = description;
        }
    }
}
