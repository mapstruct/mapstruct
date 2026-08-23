/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4038;

import java.util.List;
import java.util.Set;

public class Source {

    private String name;
    private List<String> items;
    private List<String> moreItems;
    private Set<String> extraItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public List<String> getMoreItems() {
        return moreItems;
    }

    public void setMoreItems(List<String> moreItems) {
        this.moreItems = moreItems;
    }

    public Set<String> getExtraItems() {
        return extraItems;
    }

    public void setExtraItems(Set<String> extraItems) {
        this.extraItems = extraItems;
    }
}
