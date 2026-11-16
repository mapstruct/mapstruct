/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3807;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3807Mapper {
    Issue3807Mapper INSTANCE = Mappers.getMapper( Issue3807Mapper.class );

    TargetWithoutSetter<String> mapNoSetter(Source target);

    NormalTarget<String> mapNormalSource(Source target);

    class Source {
        private final String value;

        public Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    //CHECKSTYLE:OFF
    class TargetWithoutSetter<T> {
        public T value;
    }
    //CHECKSTYLE:ON

    class NormalTarget<T> {
        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
