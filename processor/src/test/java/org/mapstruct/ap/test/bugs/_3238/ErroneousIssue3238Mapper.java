/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3238;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ErroneousIssue3238Mapper {

    @Mapping(target = ".", ignore = true)
    Target map(Source source);

    class Target {

        private final String value;

        public Target(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class Source {

        private final String value;

        public Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
