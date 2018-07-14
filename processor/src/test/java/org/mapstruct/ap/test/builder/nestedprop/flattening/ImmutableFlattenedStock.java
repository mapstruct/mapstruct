/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.flattening;

public class ImmutableFlattenedStock {

    private final String article1;
    private final String article2;
    private final int articleCount;

    public ImmutableFlattenedStock(String article1, String article2, int count) {
        this.article1 = article1;
        this.article2 = article2;
        this.articleCount = count;
    }

    public String getArticle1() {
        return article1;
    }

    public String getArticle2() {
        return article2;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public static ImmutableFlattenedStock.Builder builder() {
        return new ImmutableFlattenedStock.Builder();
    }

    public static class Builder {

        private String article1;
        private String article2;
        private int articleCount;

        public Builder() {
        }

        public Builder setArticle1(String article1) {
            this.article1 = article1;
            return this;
        }

        public Builder setArticle2(String article2) {
            this.article2 = article2;
            return this;
        }

        public Builder setArticleCount(int articleCount) {
            this.articleCount = articleCount;
            return this;
        }

        public ImmutableFlattenedStock build() {
            return new ImmutableFlattenedStock( article1, article2, articleCount );
        }

    }
}
