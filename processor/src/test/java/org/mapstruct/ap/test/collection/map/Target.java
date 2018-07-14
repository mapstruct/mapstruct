/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.map;

import java.util.LinkedHashMap;
import java.util.Map;

import org.mapstruct.ap.test.collection.map.other.ImportedType;

public class Target {
    //CHECKSTYLE:OFF
    public Map<String, String> publicValues;
    //CHECKSTYLE:ON

    private Map<String, String> values;
    private LinkedHashMap<String, ImportedType> stringEnumMap;

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public LinkedHashMap<String, ImportedType> getStringEnumMap() {
        return stringEnumMap;
    }

    public void setStringEnumMap(LinkedHashMap<String, ImportedType> stringEnumMap) {
        this.stringEnumMap = stringEnumMap;
    }
}
