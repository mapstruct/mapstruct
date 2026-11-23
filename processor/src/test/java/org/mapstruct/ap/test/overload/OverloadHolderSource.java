/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overload;

import org.jspecify.annotations.Nullable;

public class OverloadHolderSource {

    @Nullable
    private OverloadA a;

    @Nullable
    private OverloadB b;

    @Nullable
    public OverloadA getA() {
        return a;
    }

    public void setA(@Nullable OverloadA a) {
        this.a = a;
    }

    @Nullable
    public OverloadB getB() {
        return b;
    }

    public void setB(@Nullable OverloadB b) {
        this.b = b;
    }
}
