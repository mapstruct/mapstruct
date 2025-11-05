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

import com.google.protobuf.MessageOrBuilder;
import org.mapstruct.ap.test.protobuf.source.UserProto;

/**
 * Mock protobuf builder class to simulate protobuf generated builder code.
 * Used for testing reverse mapping (from DTO to protobuf).
 */
public class UserProtoBuilder implements MessageOrBuilder {

    private List<String> items = new ArrayList<>();
    private Map<String, Integer> attributes = new HashMap<>();

    // Protobuf-style getter for repeated field
    public List<String> getItemsList() {
        return items;
    }

    // Protobuf-style adder for repeated field: addItems()
    public UserProtoBuilder addItems(String item) {
        items.add(item);
        return this;
    }

    // Protobuf-style getter for map field
    public Map<String, Integer> getAttributesMap() {
        return attributes;
    }

    // Protobuf-style putter for map field: putAttributes()
    public UserProtoBuilder putAttributes(String key, Integer value) {
        attributes.put(key, value);
        return this;
    }

    // Protobuf-style putAll for map field: putAllAttributes()
    public UserProtoBuilder putAllAttributes(Map<String, Integer> map) {
        attributes.putAll(map);
        return this;
    }

    public UserProto build() {
        UserProto proto = new UserProto();
        proto.setItemsList(new ArrayList<>(items));
        proto.setAttributesMap(new HashMap<>(attributes));
        return proto;
    }
}
