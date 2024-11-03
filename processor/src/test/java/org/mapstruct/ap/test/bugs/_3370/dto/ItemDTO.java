/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3370.dto;

import java.util.Map;

public class ItemDTO {
    private final String id;
    private final Map<String, String> attributes;

    public ItemDTO(String id, Map<String, String> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
