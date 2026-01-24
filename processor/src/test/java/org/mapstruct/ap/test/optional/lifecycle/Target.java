/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.lifecycle;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class Builder {

        private String value;

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Target build() {
            Target target = new Target();
            target.setValue( value );
            return target;
        }
    }

}
