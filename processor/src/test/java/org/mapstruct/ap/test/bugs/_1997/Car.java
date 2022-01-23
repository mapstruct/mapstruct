/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1997;

/**
 * @author Filip Hrisafov
 */
public class Car {
    private String model;

    private Car(Builder builder) {
        this.model = builder.model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String model;

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Car build() {
            return new Car( this );
        }

    }
}
