/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf._target;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple target DTO with adder method.
 */
public class SimpleUserDto {

    private List<String> items;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    // Adder method for items (should be used when mapping from protobuf repeated field)
    public void addItem(String item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }
}
