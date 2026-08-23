/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4038;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Mimics a Lombok {@code @Value @Builder} type with {@code @Singular("addItem")} on {@code items}.
 */
public class Target {

    private final String name;
    private final List<String> items;
    private final List<String> moreItems;
    private final Set<String> extraItems;

    private Target(Builder builder) {
        this.name = builder.name;
        this.items = builder.items;
        this.moreItems = builder.moreItems;
        this.extraItems = builder.extraItems;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public List<String> getItems() {
        return items;
    }

    public List<String> getMoreItems() {
        return moreItems;
    }

    public Set<String> getExtraItems() {
        return extraItems;
    }

    public static class Builder {

        private String name;
        private List<String> items = new ArrayList<String>();
        private List<String> moreItems;
        private Set<String> extraItems;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder addItem(String item) {
            this.items.add( item );
            return this;
        }

        public Builder items(Collection<? extends String> items) {
            this.items.clear();
            if ( items != null ) {
                this.items.addAll( items );
            }
            return this;
        }

        public Builder moreItems(Collection<? extends String> moreItems) {
            this.moreItems = moreItems == null ? null : new ArrayList<String>( moreItems );
            return this;
        }

        public Builder extraItems(Collection<? extends String> extraItems) {
            this.extraItems = extraItems == null ? null : new LinkedHashSet<String>( extraItems );
            return this;
        }

        public Target build() {
            return new Target( this );
        }
    }
}
