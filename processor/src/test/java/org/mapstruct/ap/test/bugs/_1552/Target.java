/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1552;

/**
 * @author Filip Hrisafov
 */
public class Target {
    private Inner first;
    private Inner second;

    public Inner getFirst() {
        return first;
    }

    public void setFirst(Inner first) {
        this.first = first;
    }

    public Inner getSecond() {
        return second;
    }

    public void setSecond(Inner second) {
        this.second = second;
    }

    public static class Inner {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
