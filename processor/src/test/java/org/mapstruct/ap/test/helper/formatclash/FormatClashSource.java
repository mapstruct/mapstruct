/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.formatclash;

public class FormatClashSource {

    private Value value;

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public static class Value {

        private final Integer number;
        private final java.time.LocalDate date;

        public Value(Integer number, java.time.LocalDate date) {
            this.number = number;
            this.date = date;
        }

        public Integer getNumber() {
            return number;
        }

        public java.time.LocalDate getDate() {
            return date;
        }
    }
}
