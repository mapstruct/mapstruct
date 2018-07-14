/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractBuilder;

public abstract class AbstractProductBuilder<T extends AbstractImmutableProduct> {

    protected String name;

    public AbstractProductBuilder<T> name(String name) {
        this.name = name;
        return this;
    }

    abstract T build();
}
