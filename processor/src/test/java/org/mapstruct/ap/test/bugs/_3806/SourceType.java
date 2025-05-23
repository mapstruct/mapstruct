/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3806;

import java.util.Map;

public class SourceType {
    Map<String, String> noSetterValue;
    Map<String, String> normalValue;

    public Map<String, String> getNoSetterValue() {
        return noSetterValue;
    }

    public void setNoSetterValue(Map<String, String> noSetterValue) {
        this.noSetterValue = noSetterValue;
    }

    public Map<String, String> getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(Map<String, String> normalValue) {
        this.normalValue = normalValue;
    }
}
