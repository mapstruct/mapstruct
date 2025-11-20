/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3732;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3732Mapper {

    Target map(Source source);

    Source map(Target source);

    class Source {
        private LocalDateTime value;

        public LocalDateTime getValue() {
            return value;
        }

        public void setValue(LocalDateTime value) {
            this.value = value;
        }
    }

    class Target {

        private LocalDate value;

        public LocalDate getValue() {
            return value;
        }

        public void setValue(LocalDate value) {
            this.value = value;
        }
    }
}
