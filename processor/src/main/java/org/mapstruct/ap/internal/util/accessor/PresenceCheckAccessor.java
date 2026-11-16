/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import javax.lang.model.element.ExecutableElement;

/**
 * Accessor for presence checks.
 *
 * @author Filip Hrisafov
 */
public interface PresenceCheckAccessor {

    String getPresenceCheckSuffix();

    static PresenceCheckAccessor methodInvocation(ExecutableElement element) {
        return suffix( "." + element.getSimpleName() + "()" );
    }

    static PresenceCheckAccessor mapContainsKey(String propertyName) {
        return suffix( ".containsKey( \"" + propertyName + "\" )" );
    }

    static PresenceCheckAccessor suffix(String suffix) {
        return () -> suffix;
    }

}
