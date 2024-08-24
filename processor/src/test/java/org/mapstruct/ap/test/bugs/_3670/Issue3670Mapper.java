/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3670;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3670Mapper {

    @Mapping(target = "name", source = ".", qualifiedByName = "nestedName")
    Target map(Source source);

    @InheritInverseConfiguration
    @Mapping(target = "nested.nestedName", source = "name")
    Source map(Target target);

    @Named("nestedName")
    default String mapNestedName(Source source) {
        if ( source == null ) {
            return null;
        }

        Nested nested = source.getNested();

        return nested != null ? nested.getNestedName() : null;
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

    class Nested {
        private String nestedName;

        public String getNestedName() {
            return nestedName;
        }

        public void setNestedName(String nestedName) {
            this.nestedName = nestedName;
        }
    }

    class Source {

        private Nested nested;

        public Nested getNested() {
            return nested;
        }

        public void setNested(Nested nested) {
            this.nested = nested;
        }
    }
}
