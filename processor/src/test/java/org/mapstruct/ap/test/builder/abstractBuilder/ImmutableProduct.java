/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractBuilder;

public class ImmutableProduct extends AbstractImmutableProduct {

    private final Integer price;

    public ImmutableProduct(ImmutableProductBuilder builder) {
        super( builder );
        this.price = builder.price;
    }

    public static ImmutableProductBuilder builder() {
        return new ImmutableProductBuilder();
    }

    public Integer getPrice() {
        return price;
    }

    public static class ImmutableProductBuilder extends AbstractProductBuilder<ImmutableProduct> {
        private Integer price;

        public ImmutableProductBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        @Override
        public ImmutableProduct build() {
            return new ImmutableProduct( this );
        }
    }
}
