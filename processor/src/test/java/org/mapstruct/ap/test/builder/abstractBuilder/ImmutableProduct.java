/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractBuilder;

public class ImmutableProduct extends AbstractImmutableProduct {

    private final Integer price;
    private final Integer settlementPrice;
    private final boolean issuer;

    public ImmutableProduct(ImmutableProductBuilder builder) {
        super( builder );
        this.price = builder.price;
        this.settlementPrice = builder.settlementPrice;
        this.issuer = builder.issuer;
    }

    public static ImmutableProductBuilder builder() {
        return new ImmutableProductBuilder();
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getSettlementPrice() {
        return settlementPrice;
    }

    public boolean getIssuer() {
        return issuer;
    }

    public static class ImmutableProductBuilder extends AbstractProductBuilder<ImmutableProduct> {
        private Integer price;
        private Integer settlementPrice;
        private boolean issuer;

        public ImmutableProductBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        // Builder setter method is 'similar' to JavaBean setter starting with 'set'
        public ImmutableProductBuilder settlementPrice(Integer settlementPrice) {
            this.settlementPrice = settlementPrice;
            return this;
        }

        // Builder setter method is 'similar' to JavaBean setter starting with 'is'
        public ImmutableProductBuilder issuer(boolean issuer) {
            this.issuer = issuer;
            return this;
        }

        @Override
        public ImmutableProduct build() {
            return new ImmutableProduct( this );
        }
    }
}
