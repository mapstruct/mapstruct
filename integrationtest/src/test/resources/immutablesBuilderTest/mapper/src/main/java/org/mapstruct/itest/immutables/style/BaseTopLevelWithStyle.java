/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.style;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(typeImmutable = "Fixed*", typeAbstract = "Base*")
public abstract class BaseTopLevelWithStyle {
    public abstract Child getChild();

    @Value.Immutable
    interface Child {
        String getString();
    }
}
