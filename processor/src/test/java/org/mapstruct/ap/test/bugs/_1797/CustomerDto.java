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
public class CustomerDto {

    public enum Type {
        ONE, TWO
    }

    private EnumSet<Type> types;

    public EnumSet<Type> getTypes() {
        return types;
    }

    public void setTypes(EnumSet<Type> types) {
        this.types = types;
    }
}
