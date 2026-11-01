/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.localvarclash;

import org.jspecify.annotations.Nullable;

public class ClashSourceBean {

    @Nullable
    private ClashInput clashInput;

    @Nullable
    public ClashInput getClashInput() {
        return clashInput;
    }

    public void setClashInput(@Nullable ClashInput clashInput) {
        this.clashInput = clashInput;
    }
}
