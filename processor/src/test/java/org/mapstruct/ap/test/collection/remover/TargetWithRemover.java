/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.remover;

import java.util.ArrayList;
import java.util.List;

public class TargetWithRemover {
    private boolean removed;
    private List<String> strings = new ArrayList<>();

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public TargetWithRemover removed(boolean removed) {
        this.removed = removed;
        return this;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public TargetWithRemover strings(List<String> strings) {
        this.strings.addAll( strings );
        return this;
    }

    @SuppressWarnings("unused")
    public TargetWithRemover addString(String string) {
        strings.add( string );
        return this;
    }

    @SuppressWarnings("unused")
    public TargetWithRemover removeString(String string) {
        strings.remove( string );
        return this;
    }
}
