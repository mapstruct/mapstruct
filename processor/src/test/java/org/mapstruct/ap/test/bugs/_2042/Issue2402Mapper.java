/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2042;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2402Mapper {

    Issue2402Mapper INSTANCE = Mappers.getMapper( Issue2402Mapper.class );

    @Mapping(target = ".", source = "source.info")
    Target map(Source source, String other);

    class Target {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Source {

        private final Info info;

        public Source(Info info) {
            this.info = info;
        }

        public Info getInfo() {
            return info;
        }
    }

    class Info {
        private final String name;

        public Info(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
