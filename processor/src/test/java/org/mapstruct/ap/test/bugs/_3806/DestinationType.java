/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3806;

import java.util.Map;

public interface DestinationType {
    Map<String, String> getNoSetterValue();

    Map<String, String> getNormalValue();

    void setNormalValue(Map<String, String> normalValue);

}
