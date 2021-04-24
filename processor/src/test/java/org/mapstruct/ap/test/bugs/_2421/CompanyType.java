/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2421;

public enum CompanyType {
    insurance("Insurance", "insurance"),
    inspection("Inspection", "inspectionem"),
    Shipping("Shipping", "naviportans");

    private final String title;
    private final String latinTitle;

    CompanyType(String title, String latinTitle) {
        this.title = title;
        this.latinTitle = latinTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getLatinTitle() {
        return latinTitle;
    }
}
