/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1997;

/**
 * @author Filip Hrisafov
 */
public class CarInsurance {
    private CarDetail detail;

    private CarInsurance(Builder builder) {
        this.detail = builder.detail;
    }

    public CarDetail getDetail() {
        return detail;
    }

    public void setDetail(CarDetail detail) {
        this.detail = detail;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private CarDetail detail;

        public Builder detail(CarDetail detail) {
            this.detail = detail;
            return this;
        }

        public CarInsurance build() {
            return new CarInsurance( this );
        }

    }
}
