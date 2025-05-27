/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3865;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Source type class implementing DestinationType for testing null value property mapping strategy with Map properties.
 */
public class SourceType implements DestinationType {
    private Map<String, String> attributes;
    private List<String> items;

    private Map<String, String> initializedAttributes = new HashMap<>();
    private List<String> initializedItems = new ArrayList<>();

    public SourceType() {
        initializedAttributes.put( "key1", "value1" );
        initializedItems.add( "item1" );
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, String> getInitializedAttributes() {
        return initializedAttributes;
    }

    @Override
    public void setInitializedAttributes(Map<String, String> attributes) {
        this.initializedAttributes = attributes;
    }

    @Override
    public List<String> getItems() {
        return items;
    }

    @Override
    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public List<String> getInitializedItems() {
        return initializedItems;
    }

    @Override
    public void setInitializedItems(List<String> items) {
        this.initializedItems = items;
    }
}
