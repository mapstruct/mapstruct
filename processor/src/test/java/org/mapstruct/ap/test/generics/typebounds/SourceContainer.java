/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.typebounds;

/**
 * @author Ben Zegveld
 */
public class SourceContainer {
    private Source<?> contained;

    public Source<?> getContained() {
        return contained;
    }

    public void setContained(Source<?> contained) {
        this.contained = contained;
    }
}
