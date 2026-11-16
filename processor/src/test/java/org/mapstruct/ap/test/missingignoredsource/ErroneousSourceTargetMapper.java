/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.missingignoredsource;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ErroneousSourceTargetMapper {

    @BeanMapping(ignoreUnmappedSourceProperties = "bar")
    Target map(Target source);

    class Target {
        private final String value;

        public Target(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
