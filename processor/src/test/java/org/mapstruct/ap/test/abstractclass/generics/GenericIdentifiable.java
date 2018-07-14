/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass.generics;

/**
 * @author Andreas Gudian
 */
public interface GenericIdentifiable<X> {
    X getKey();

    void setKey(X id);
}
