/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3158;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3158Mapper {

    Issue3158Mapper INSTANCE = Mappers.getMapper( Issue3158Mapper.class );

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name")
    Target map(Target target);

    class Target {
        private final String name;
        private final String email;

        public Target(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }

}
