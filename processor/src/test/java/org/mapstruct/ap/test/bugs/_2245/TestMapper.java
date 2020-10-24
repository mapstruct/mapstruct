/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2245;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestMapper {

    TestMapper INSTANCE = Mappers.getMapper( TestMapper.class );

    class Tenant {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    class Inner {
        private final String id;

        public Inner(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    class TenantDTO {
        private final Inner inner;

        public TenantDTO(Inner inner) {
            this.inner = inner;
        }

        public Inner getInner() {
            return inner;
        }
    }

    @Mapping(target = "id", source = "inner.id", defaultValue = "test")
    Tenant map(TenantDTO tenant);
}
