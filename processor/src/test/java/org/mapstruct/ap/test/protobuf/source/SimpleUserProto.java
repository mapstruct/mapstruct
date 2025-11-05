/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.protobuf.source;

import java.util.List;

import com.google.protobuf.MessageOrBuilder;

/**
 * Simple protobuf message class with only repeated field.
 */
public class SimpleUserProto implements MessageOrBuilder {

    private List<String> items;

    // Protobuf-style getter for repeated field: getItemsList()
    public List<String> getItemsList() {
        return items;
    }

    public void setItemsList(List<String> items) {
        this.items = items;
    }
}
