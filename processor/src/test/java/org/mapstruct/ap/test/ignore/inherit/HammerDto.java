/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore.inherit;

/**
 *
 * @author Sjaak Derksen
 */
public class HammerDto extends ToolDto {

    private Integer toolSize;

    public Integer getToolSize() {
        return toolSize;
    }

    public void setToolSize(Integer toolSize) {
        this.toolSize = toolSize;
    }
}
