/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.verbose;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SelectBeanMapping {

    SelectBeanMapping INSTANCE = Mappers.getMapper( SelectBeanMapping.class );

    Target map(Source source);

    NestedTarget map(NestedSource source);

    class Source {
        private NestedSource nested;

        public NestedSource getNested() {
            return nested;
        }

        public void setNested(NestedSource nested) {
            this.nested = nested;
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

    class NestedSource {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

}

