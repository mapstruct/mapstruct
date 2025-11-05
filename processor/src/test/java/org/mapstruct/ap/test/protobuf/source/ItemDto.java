/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf.source;

import java.util.List;
import java.util.Map;

/**
 * Simple DTO for testing reverse mapping (DTO to protobuf).
 */
public class ItemDto {

    private List<String> items;
    private Map<String, Integer> attributes;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Integer> attributes) {
        this.attributes = attributes;
    }
}
