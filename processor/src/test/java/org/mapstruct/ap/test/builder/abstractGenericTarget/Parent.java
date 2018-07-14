/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractGenericTarget;

public interface Parent<T extends Child> {
    int getCount();

    T getChild();

    Child getNonGenericChild();
}
