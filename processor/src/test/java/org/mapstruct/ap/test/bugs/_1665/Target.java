/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1665;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arne Seime
 */
public class Target {

    private List<Integer> value = new ArrayList<>();

    public void addValue(int v) {
        value.add( v );
    }

    public List<Integer> getValue() {
        return value;
    }
}
