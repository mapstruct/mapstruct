/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods;

/**
 *
 * @author Sjaak Derksen
 */
public class OrganizationWithoutTypeGetterEntity {

    private CompanyEntity company;
    private OrganizationTypeEntity type;

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public void setType(OrganizationTypeEntity type) {
        this.type = type;
    }
}
