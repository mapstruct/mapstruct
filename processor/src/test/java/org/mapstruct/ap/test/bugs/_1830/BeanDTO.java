/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1830;

import java.util.Collection;

public class BeanDTO {
    private String id;

    private Collection<String> list;

    public Collection<String> getList() {
        return list;
    }

    public void setList(Collection<String> list) {
        this.list = list;
    }

}
