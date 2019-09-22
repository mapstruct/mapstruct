/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1799;

import java.util.Date;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private final Date settlementDate;
    private final String getawayLocation;

    public Source(Date settlementDate, String getawayLocation) {
        this.settlementDate = settlementDate;
        this.getawayLocation = getawayLocation;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public String getGetawayLocation() {
        return getawayLocation;
    }
}
