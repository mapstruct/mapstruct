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
public class OrganizationEntity {

    private CompanyEntity company;
    private OrganizationTypeEntity type;
    private OrganizationTypeNrEntity typeNr;

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public OrganizationTypeEntity getType() {
        return type;
    }

    public void setType(OrganizationTypeEntity type) {
        this.type = type;
    }

    public OrganizationTypeNrEntity getTypeNr() {
        return typeNr;
    }

    public void setTypeNr(OrganizationTypeNrEntity typeNr) {
        this.typeNr = typeNr;
    }

}
