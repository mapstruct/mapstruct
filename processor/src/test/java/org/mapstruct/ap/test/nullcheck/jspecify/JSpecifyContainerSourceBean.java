/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JSpecifyContainerSourceBean {

    private List<String> values;
    private Map<String, String> entries;
    private Stream<String> items;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Map<String, String> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, String> entries) {
        this.entries = entries;
    }

    public Stream<String> getItems() {
        return items;
    }

    public void setItems(Stream<String> items) {
        this.items = items;
    }
}
