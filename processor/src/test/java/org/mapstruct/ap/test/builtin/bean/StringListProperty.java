/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.bean;

import java.util.List;

public class StringListProperty {

    // CHECKSTYLE:OFF
    public List<String> publicProp;
    // CHECKSTYLE:ON

    private List<String> prop;

    public List<String> getProp() {
        return prop;
    }

    public void setProp( List<String> prop ) {
        this.prop = prop;
    }
}
