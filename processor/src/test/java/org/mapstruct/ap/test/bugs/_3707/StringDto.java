/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3707;

public class StringDto extends AbstractDto {
    private final String value;

    public static class Builder extends AbstractDto.Builder<Builder> {

        private String value;

        @Override
        protected Builder self() {
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return self();
        }

        public Builder name(String value) {
            return super.name( value );
        }

        public Builder id(Integer value) {
            return super.id( value );
        }

        @Override
        public StringDto build() {
            return new StringDto( this );
        }
    }

    private StringDto(Builder builder) {
        super( builder );
        value = builder.value;
    }

    public String getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }
}
