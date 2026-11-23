/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.contextlost;

import org.jspecify.annotations.Nullable;

public class ContextLostSourceBean {

    @Nullable
    private ContextLostInput input;

    @Nullable
    public ContextLostInput getInput() {
        return input;
    }

    public void setInput(@Nullable ContextLostInput input) {
        this.input = input;
    }
}
