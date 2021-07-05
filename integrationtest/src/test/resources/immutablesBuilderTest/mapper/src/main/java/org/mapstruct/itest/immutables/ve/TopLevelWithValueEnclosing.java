/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.ve;

import org.immutables.value.Value;

@Value.Immutable
@Value.Enclosing
public interface TopLevelWithValueEnclosing {
    Child getChild();

    @Value.Immutable
    interface Child {
        String getString();
    }
}

