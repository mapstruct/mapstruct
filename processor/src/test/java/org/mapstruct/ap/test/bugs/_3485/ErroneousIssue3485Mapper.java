/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3485;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author hduelme
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ErroneousIssue3485Mapper {

    ErroneousIssue3485Mapper INSTANCE = Mappers.getMapper( ErroneousIssue3485Mapper.class );
    class Target {
        private final String value;

        public Target( String value ) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    @Mapping(target = ".")
    Target targetFromExpression(String s);
}
