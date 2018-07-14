/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.lifecycle;

/**
 * @author Filip Hrisafov
 */
public class Item {

    private final String name;

    Item(Builder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Item create() {
            return new Item( this );
        }
    }
}
