/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

/**
 *
 * @author Sjaak Derksen
 */
public class AppleFamilyDto {

    private AppleDto apple;

    public AppleDto getApple() {
        return apple;
    }

    public void setApple(AppleDto apple) {
        this.apple = apple;
    }

}
