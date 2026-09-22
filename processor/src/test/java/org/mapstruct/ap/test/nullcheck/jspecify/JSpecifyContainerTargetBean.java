/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class JSpecifyContainerTargetBean {

    private List<String> values;
    private boolean valuesSet;

    private Map<String, String> entries;
    private boolean entriesSet;

    private Stream<String> items;
    private boolean itemsSet;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.valuesSet = true;
        this.values = values;
    }

    public boolean isValuesSet() {
        return valuesSet;
    }

    public Map<String, String> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, String> entries) {
        this.entriesSet = true;
        this.entries = entries;
    }

    public boolean isEntriesSet() {
        return entriesSet;
    }

    public Stream<String> getItems() {
        return items;
    }

    public void setItems(Stream<String> items) {
        this.itemsSet = true;
        this.items = items;
    }

    public boolean isItemsSet() {
        return itemsSet;
    }
}
