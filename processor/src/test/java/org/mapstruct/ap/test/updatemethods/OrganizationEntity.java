/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
