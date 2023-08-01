/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.defaultimplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andreas Gudian
 *
 */
public class NoSetterTarget {
    private List<String> listValues = new ArrayList<>();
    private Map<String, String> mapValues = new HashMap<>();

    public List<String> getListValues() {
        return listValues;
    }

    public Map<String, String> getMapValues() {
        return mapValues;
    }
}
