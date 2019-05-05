/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1797;

import java.util.EnumSet;

/**
 * @author Filip Hrisafov
 */
public class Customer {

    public enum Type {
        ONE, TWO
    }

    private final EnumSet<Type> types;

    public Customer(EnumSet<Type> types) {
        this.types = types;
    }

    public EnumSet<Type> getTypes() {
        return types;
    }
}
