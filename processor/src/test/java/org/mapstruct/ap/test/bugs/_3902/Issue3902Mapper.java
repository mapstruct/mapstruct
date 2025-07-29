/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3902;

import org.mapstruct.Ignored;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for testing bug #3902.
 *
 * @author znight1020
 */
@Mapper
public interface Issue3902Mapper {

    Issue3902Mapper INSTANCE = Mappers.getMapper( Issue3902Mapper.class );

    @Ignored(targets = {"name", "foo", "bar"})
    ZooDto mapWithOneKnownAndMultipleUnknowns(Zoo source);

    @Ignored(targets = {"name", "address", "foo"})
    ZooDto mapWithMultipleKnownAndOneUnknown(Zoo source);

    class Zoo {
        private String name;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    class ZooDto {
        private String name;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
