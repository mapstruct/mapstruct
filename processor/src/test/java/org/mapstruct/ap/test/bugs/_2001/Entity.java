/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2001;

import java.util.Set;

/**
 * @author Filip Hrisafov
 */
public
class Entity {

    private Set<EntityExtra> extras;

    public Set<EntityExtra> getExtras() {
        return extras;
    }

    public void setExtras(Set<EntityExtra> extras) {
        this.extras = extras;
    }
}
