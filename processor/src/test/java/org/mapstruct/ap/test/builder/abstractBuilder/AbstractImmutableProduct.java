/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractBuilder;

/**
 * @author Filip Hrisafov
 */
public abstract class AbstractImmutableProduct {

    private final String name;

    public AbstractImmutableProduct(AbstractProductBuilder<?> builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }
}
