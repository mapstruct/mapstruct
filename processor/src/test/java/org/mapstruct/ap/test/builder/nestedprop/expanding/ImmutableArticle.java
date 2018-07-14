/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.expanding;

public class ImmutableArticle {
    private final String description;

    ImmutableArticle(ImmutableArticle.Builder builder) {
        this.description = builder.description;
    }

    public static ImmutableArticle.Builder builder() {
        return new ImmutableArticle.Builder();
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private String description;

        public ImmutableArticle build() {
            return new ImmutableArticle( this );
        }

        public ImmutableArticle.Builder description(String description) {
            this.description = description;
            return this;
        }
    }
}
