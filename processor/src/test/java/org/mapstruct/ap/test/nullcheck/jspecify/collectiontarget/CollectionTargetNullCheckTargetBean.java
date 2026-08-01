/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.collectiontarget;

import java.util.List;

import org.jspecify.annotations.NonNull;

public class CollectionTargetNullCheckTargetBean {

    private List<String> numbers;
    private boolean numbersSet;

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(@NonNull List<String> numbers) {
        this.numbersSet = true;
        this.numbers = numbers;
    }

    public boolean isNumbersSet() {
        return numbersSet;
    }
}
