/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2001;

/**
 * @author Filip Hrisafov
 */
public
class Form {

    private FormExtra[] extras;

    public FormExtra[] getExtras() {
        return extras;
    }

    public void setExtras(FormExtra[] extras) {
        this.extras = extras;
    }
}
