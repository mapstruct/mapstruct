/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2077;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper( unmappedTargetPolicy = ReportingPolicy.ERROR )
public interface Issue2077ErroneousMapper {

    Issue2077ErroneousMapper INSTANCE = Mappers.getMapper( Issue2077ErroneousMapper.class );

    @Mapping(target = "s1", defaultValue = "xyz" )
    Target map(String source);

    class Target {

        private String s1;

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1 = s1;
        }

    }
}
