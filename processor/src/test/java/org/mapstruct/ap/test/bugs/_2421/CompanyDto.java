/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2421;

public class CompanyDto {
    private String name;
    private String companyType;
    private String companyTypeLatinTitle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyTypeLatinTitle() {
        return companyTypeLatinTitle;
    }

    public void setCompanyTypeLatinTitle(String companyTypeLatinTitle) {
        this.companyTypeLatinTitle = companyTypeLatinTitle;
    }
}
