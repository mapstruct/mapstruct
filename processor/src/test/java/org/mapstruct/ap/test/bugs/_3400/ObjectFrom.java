/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3400;

class ObjectFrom {

    private Long longField;

    public Long getLongField() {
        return longField;
    }

    public ObjectFrom setLongField(Long longField) {
        this.longField = longField;
        return this;
    }
}
