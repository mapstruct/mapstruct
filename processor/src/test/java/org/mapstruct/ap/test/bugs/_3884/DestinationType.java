/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3884;

import java.util.List;
import java.util.Map;

/**
 * Destination type interface for testing null value property mapping strategy with Map properties.
 */
public interface DestinationType {
    Map<String, String> getAttributes();

    void setAttributes(Map<String, String> attributes);

    List<String> getItems();

    void setItems(List<String> items);
}
