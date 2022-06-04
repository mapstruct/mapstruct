/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2867;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2867Mapper extends Issue2867BaseMapper<Issue2867Mapper.Target, Issue2867Mapper.Source> {

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
    }
}
