/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3591;

import java.util.List;

public class BeanDto {

    private List<BeanDto> beans;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<BeanDto> getBeans() {
        return beans;
    }

    public void setBeans(List<BeanDto> beans) {
        this.beans = beans;
    }
}
