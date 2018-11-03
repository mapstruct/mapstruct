/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1576.java8;

import java.time.LocalDateTime;

public class Target {

    private LocalDateTime ldate;

    public LocalDateTime getLdate() {
        return ldate;
    }

    public void setLdate(LocalDateTime ldate) {
        this.ldate = ldate;
    }
}
