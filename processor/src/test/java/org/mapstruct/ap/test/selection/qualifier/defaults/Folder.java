/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.defaults;

import java.util.UUID;

/**
 * @author Ben Zegveld
 */
class Folder {
    private UUID id;
    private Folder ancestor;

    Folder(UUID id, Folder ancestor) {
        this.id = id;
        this.ancestor = ancestor;
    }

    public Folder getAncestor() {
        return ancestor;
    }

    public UUID getId() {
        return id;
    }
}
