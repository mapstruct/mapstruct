/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3462;

import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3462Mapper {

    Issue3462Mapper INSTANCE = Mappers.getMapper( Issue3462Mapper.class );

    Target map(Source source);

    class Source {
        private final List<String> values;

        public Source(List<String> values) {
            this.values = values;
        }

        public List<String> getValues() {
            return values;
        }

        public Stream<String> getValuesStream() {
            return values != null ? values.stream() : Stream.empty();
        }
    }

    class Target {
        private List<String> values;

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public Stream<String> getValuesStream() {
            return values != null ? values.stream() : Stream.empty();
        }
    }

}
