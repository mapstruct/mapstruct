/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4145;

import java.util.ArrayList;
import java.util.List;

public class Target {

    private final List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    public void addList(String item) {
        list.add( item );
    }
}
