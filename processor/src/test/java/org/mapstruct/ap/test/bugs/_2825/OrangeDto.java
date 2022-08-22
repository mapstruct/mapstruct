/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2825;

/**
 * @author orange add
 */
public class OrangeDto extends FruitDto {

    private Integer orangeId;

    public Integer getOrangeId() {
        return orangeId;
    }

    public void setOrangeId(Integer orangeId) {
        this.orangeId = orangeId;
    }
}
