/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._634;

import java.util.List;

public class Source<T> {

    private final List<T> items;

    public Source(List<T> items) {
        this.items = items;
    }

    public List<T> getContent() {
        return items;
    }
}
