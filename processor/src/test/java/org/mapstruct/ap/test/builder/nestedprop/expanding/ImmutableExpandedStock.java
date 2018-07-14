/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.expanding;

public class ImmutableExpandedStock {
    private final int count;
    private final ImmutableArticle first;
    private final ImmutableArticle second;

    ImmutableExpandedStock(Builder builder) {
        this.count = builder.articleCount;
        this.first = builder.first;
        this.second = builder.second;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getCount() {
        return count;
    }

    public ImmutableArticle getFirst() {
        return first;
    }

    public ImmutableArticle getSecond() {
        return second;
    }

    public static class Builder {
        private int articleCount;
        private ImmutableArticle first;
        private ImmutableArticle second;

        public Builder articleCount(int articleCount) {
            this.articleCount = articleCount;
            return this;
        }

        public Builder first(ImmutableArticle first) {
            this.first = first;
            return this;
        }

        public Builder second(ImmutableArticle second) {
            this.second = second;
            return this;
        }

        public ImmutableExpandedStock build() {
            return new ImmutableExpandedStock( this );
        }
    }
}
