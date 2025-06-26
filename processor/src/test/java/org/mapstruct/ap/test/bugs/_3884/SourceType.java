/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3884;

import java.util.List;
import java.util.Map;

/**
 * Source type class implementing DestinationType for testing null value property mapping strategy with Map properties.
 */
public class SourceType implements DestinationType {
    private Map<String, String> attributes;
    private List<String> items;

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<String> getItems() {
        return items;
    }

    @Override
    public void setItems(List<String> items) {
        this.items = items;
    }
}
