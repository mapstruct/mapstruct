/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.map;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.mapstruct.ap.test.collection.map.other.ImportedType;

public class Source {

    private Map<Long, Date> publicValues;

    private Map<Long, Date> values;
    private LinkedHashMap<String, ImportedType> stringEnumMap;

    public Map<Long, Date> getValues() {
        return values;
    }

    public void setValues(Map<Long, Date> values) {
        this.values = values;
    }

    public LinkedHashMap<String, ImportedType> getStringEnumMap() {
        return stringEnumMap;
    }

    public void setStringEnumMap(LinkedHashMap<String, ImportedType> stringEnumMap) {
        this.stringEnumMap = stringEnumMap;
    }

    public Map<Long, Date> getPublicValues() {
        return publicValues;
    }

    public void setPublicValues(Map<Long, Date> publicValues) {
        this.publicValues = publicValues;
    }
}
