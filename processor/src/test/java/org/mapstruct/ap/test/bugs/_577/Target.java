/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._577;

import java.util.List;

public class Target {

    private List<Integer> first;

    private List<Integer> second;

    public List<Integer> getFirst() {
        return first;
    }

    public void setFirst(List<Integer> first) {
        this.first = first;
    }

    public List<Integer> getSecond() {
        return second;
    }

    public void setSecond(List<Integer> second) {
        this.second = second;
    }
}
