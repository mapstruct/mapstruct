/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
