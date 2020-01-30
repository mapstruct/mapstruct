/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

public class FridgeDTO {

    private ShelveDTO shelve;

    public ShelveDTO getShelve() {
        return shelve;
    }

    public void setShelve(ShelveDTO shelve) {
        this.shelve = shelve;
    }
}
