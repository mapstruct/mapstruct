/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf.source;

import java.util.List;
import java.util.Map;

import com.google.protobuf.MessageOrBuilder;

/**
 * Mock protobuf message class to simulate protobuf generated code.
 * Simulates a User message with repeated field (items) and map field (attributes).
 */
public class UserProto implements MessageOrBuilder {

    private List<String> items;
    private Map<String, Integer> attributes;

    // Protobuf-style getter for repeated field: getItemsList()
    public List<String> getItemsList() {
        return items;
    }

    public void setItemsList(List<String> items) {
        this.items = items;
    }

    // Protobuf-style getter for map field: getAttributesMap()
    public Map<String, Integer> getAttributesMap() {
        return attributes;
    }

    public void setAttributesMap(Map<String, Integer> attributes) {
        this.attributes = attributes;
    }

    // Standard getters that should NOT be used by MapStruct
    public List<String> getItems() {
        return items;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }
}
