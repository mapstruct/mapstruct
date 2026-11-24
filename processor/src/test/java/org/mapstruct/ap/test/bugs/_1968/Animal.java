/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1968;

import java.util.List;

public class Animal {

    private List<String> names;
    private List<String> specialNames;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getSpecialNames() {
        return specialNames;
    }

    public void setSpecialNames(List<String> specialNames) {
        this.specialNames = specialNames;
    }
}
