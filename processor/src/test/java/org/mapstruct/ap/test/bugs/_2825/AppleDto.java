/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2825;

/**
 * @author orange add
 */

public class AppleDto extends FruitDto {

    private Integer appleId;

    public Integer getAppleId() {
        return appleId;
    }

    public void setAppleId(Integer appleId) {
        this.appleId = appleId;
    }
}
