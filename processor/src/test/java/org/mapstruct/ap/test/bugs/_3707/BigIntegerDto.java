/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3707;

import java.math.BigInteger;

public class BigIntegerDto extends AbstractDto {
    private final BigInteger value;

    public static class Builder extends AbstractDto.Builder<Builder> {

        private BigInteger value;

        @Override
        protected Builder self() {
            return this;
        }

        public Builder value(BigInteger value) {
            this.value = value;
            return self();
        }

        @Override
        public BigIntegerDto build() {
            return new BigIntegerDto(this);
        }
    }

    private BigIntegerDto(Builder builder) {
        super(builder);
        value = builder.value;
    }

    public BigInteger getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }
}
