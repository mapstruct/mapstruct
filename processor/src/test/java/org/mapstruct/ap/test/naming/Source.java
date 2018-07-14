/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Source {

    private List<Long> values;
    private int someNumber;
    private Map<Long, Date> map;

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }

    public int getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(int someNumber) {
        this.someNumber = someNumber;
    }

    public Map<Long, Date> getMap() {
        return map;
    }

    public void setMap(Map<Long, Date> map) {
        this.map = map;
    }
}
