/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.map;

import java.util.List;
import java.util.Map;

public class BaseDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Map<String, String> values;

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public Map<String, String> getValues() {
        return this.values;
    }

    private List<String> others;

    public void setOthers(List<String> others) {
        this.others = others;
    }

    public List<String> getOthers() {
        return this.others;
    }
}
