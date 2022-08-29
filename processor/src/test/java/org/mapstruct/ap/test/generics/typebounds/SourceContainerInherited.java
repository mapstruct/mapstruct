/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.typebounds;

/**
 * @author Ben Zegveld
 */
public class SourceContainerInherited<T extends WildcardedInterface> {
    private Source<T> contained;

    public Source<T> getContained() {
        return contained;
    }

    public void setContained(Source<T> contained) {
        this.contained = contained;
    }
}
