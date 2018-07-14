/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin._target;

import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class IterableTarget {

    // CHECKSTYLE:OFF
    public List<String> publicDates;
    // CHECKSTYLE:ON

    private List<String> dates;

    public List<String> getDates() {
        return dates;
    }

    public void setDates( List<String> dates ) {
        this.dates = dates;
    }

}
