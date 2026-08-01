/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.collectiontarget;

import java.util.List;

/**
 * Deliberately unannotated (unknown JSpecify nullability) collection getter, requiring an
 * element-type conversion (Integer -&gt; String) on the way to the target so the assignment is
 * not a DIRECT one.
 */
public class CollectionTargetNullCheckSourceBean {

    private List<Integer> numbers;

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }
}
