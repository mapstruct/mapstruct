/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._405;

import org.mapstruct.TargetType;

public class EntityFactory {

    public <T> T createEntity(@TargetType Class<T> entityClass) {
        return null;
    }
}
