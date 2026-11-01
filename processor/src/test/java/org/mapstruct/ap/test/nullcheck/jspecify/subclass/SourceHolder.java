/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.subclass;

import org.jspecify.annotations.NonNull;

public class SourceHolder {
    private Source1 h;

    @NonNull
    public Source1 getH() {
        return h;
    }

    public void setH(Source1 h) {
        this.h = h;
    }
}
