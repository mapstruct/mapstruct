/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapValueArrayMapper {

    MapValueArrayMapper INSTANCE = Mappers.getMapper( MapValueArrayMapper.class );

    Target map(Map<String, String[]> source);

    class Target {

        private Integer[] ids;

        public Integer[] getIds() {
            return ids;
        }

        public void setIds(Integer[] ids) {
            this.ids = ids;
        }
    }
}
