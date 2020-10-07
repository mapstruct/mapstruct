/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultvalue;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousMissingSourceMapper {

    @Mapping(target = "type", defaultValue = "STANDARD")
    Tenant map(TenantDto tenant);

    class Tenant {

        private final String id;
        private final String type;

        public Tenant(String id, String type) {
            this.id = id;
            this.type = type;
        }
    }

    class TenantDto {

        private final String id;

        public TenantDto(String id) {
            this.id = id;
        }
    }
}
