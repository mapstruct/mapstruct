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
public class Target {

    private final Date settlementDate;
    private final String getawayLocation;

    public Target(Builder builder) {
        this.settlementDate = builder.settlementDate;
        this.getawayLocation = builder.getawayLocation;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public String getGetawayLocation() {
        return getawayLocation;
    }

    public static Target.Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Date settlementDate;
        private String getawayLocation;

        public Builder settlementDate(Date settlementDate) {
            this.settlementDate = settlementDate;
            return this;
        }

        public Builder getawayLocation(String getawayLocation) {
            this.getawayLocation = getawayLocation;
            return this;
        }

        public Target build() {
            return new Target( this );
        }
    }
}
