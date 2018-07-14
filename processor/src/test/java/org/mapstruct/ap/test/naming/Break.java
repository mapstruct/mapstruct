/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming;

import java.util.List;
import java.util.Map;

public class Break {

    private List<String> values;
    private String someNumber;
    private Map<String, String> map;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(String someNumber) {
        this.someNumber = someNumber;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
