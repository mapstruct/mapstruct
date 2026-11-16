/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.Set;

/**
 * Marker interface for presence checks.
 *
 * @author Filip Hrisafov
 */
public interface PresenceCheck {

    /**
     * returns all types required as import by the presence check.
     *
     * @return imported types
     */
    Set<Type> getImportTypes();

    PresenceCheck negate();

}
