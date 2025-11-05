/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf._target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Target DTO with standard JavaBeans accessors.
 */
public class UserDto {

    private List<String> items;
    private Map<String, Integer> attributes;

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

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Integer> attributes) {
        this.attributes = attributes;
    }

    // Putter method for attributes (should be used when mapping from protobuf map field)
    public void putAttribute(String key, Integer value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(key, value);
    }

    // putAll method for attributes
    public void putAllAttributes(Map<String, Integer> map) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.putAll(map);
    }
}
