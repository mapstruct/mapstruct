/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3949;

public interface ParentTargetInterface {
    ParentTarget getChild();

    void setChild(String child);

    void setChild(ParentTarget child);
}
