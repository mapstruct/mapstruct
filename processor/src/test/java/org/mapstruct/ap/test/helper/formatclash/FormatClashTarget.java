/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.formatclash;

public class FormatClashTarget {

    private String decimal;
    private String grouped;
    private String sameFormat;
    private String isoDate;
    private String germanDate;

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getGrouped() {
        return grouped;
    }

    public void setGrouped(String grouped) {
        this.grouped = grouped;
    }

    public String getSameFormat() {
        return sameFormat;
    }

    public void setSameFormat(String sameFormat) {
        this.sameFormat = sameFormat;
    }

    public String getIsoDate() {
        return isoDate;
    }

    public void setIsoDate(String isoDate) {
        this.isoDate = isoDate;
    }

    public String getGermanDate() {
        return germanDate;
    }

    public void setGermanDate(String germanDate) {
        this.germanDate = germanDate;
    }
}
