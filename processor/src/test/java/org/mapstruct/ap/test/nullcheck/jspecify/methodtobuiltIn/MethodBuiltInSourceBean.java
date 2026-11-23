/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtobuiltIn;

import org.jspecify.annotations.Nullable;

public class MethodBuiltInSourceBean {

    @Nullable
    private MethodBuiltInInput input;

    @Nullable
    public MethodBuiltInInput getInput() {
        return input;
    }

    public void setInput(@Nullable MethodBuiltInInput input) {
        this.input = input;
    }
}
