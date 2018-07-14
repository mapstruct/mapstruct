/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.lifecycle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Order {

    private final List<Item> items;
    private final String creator;

    public Order(Builder builder) {
        this.items = new ArrayList<Item>( builder.items );
        this.creator = builder.creator;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getCreator() {
        return creator;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Item> items = new ArrayList<Item>();
        private String creator;

        public Builder items(List<Item> items) {
            this.items = items;
            return this;
        }

        public Order create() {
            return new Order( this );
        }
    }
}
