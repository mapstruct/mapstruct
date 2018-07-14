/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.freebuilder;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public abstract class Person {
    public abstract String getName();
    public abstract int getAge();
    public abstract Address getAddress();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Person_Builder { }
}
