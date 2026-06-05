/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3948;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface Issue3948Mapper {

    class ComparableField implements Comparable<ComparableField> {

        private final String code;

        public ComparableField(String code) {
            this.code = code;
        }

        @Override
        public int compareTo(ComparableField o) {
            return code.compareTo( o.code );
        }
    }

    class Id<T> implements Comparable<Id<T>> {
        private final long value;

        public Id(long value) {
            this.value = value;
        }

        @Override
        public int compareTo(Id<T> o) {
            return Long.compare( value, o.value );
        }
    }

    class From {
        private long id;
        private String code;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    class To {
        private Id<To> id;
        private ComparableField comparableField;

        public Id<To> getId() {
            return id;
        }

        public void setId(Id<To> id) {
            this.id = id;
        }

        public ComparableField getComparableField() {
            return comparableField;
        }

        public void setComparableField(ComparableField comparableField) {
            this.comparableField = comparableField;
        }
    }

    @Mapping(source = "code", target = "comparableField")
    To convert(From from);

    default <T> Id<T> longToId(Long id) {
        return id == null ? null : new Id<>(id);
    }

    default ComparableField convert(String code) {
        return code == null ? null : new ComparableField(code);
    }
}
