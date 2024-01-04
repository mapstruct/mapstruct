/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2781;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Mengxing Yuan
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface Issue2781Mapper {

    Issue2781Mapper INSTANCE = Mappers.getMapper( Issue2781Mapper.class );

    @Mapping(target = "nested", source = "source")
    Target map(Source source);

    class Target {
        private Source nested;

        public Source getNested() {
            return nested;
        }

        public void setNested(Source nested) {
            this.nested = nested;
        }
    }

    class Source {
        private String field1;
        private String field2;

        public Source(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public String getField1() {
            return field1;
        }

        public String getField2() {
            return field2;
        }
    }
}
