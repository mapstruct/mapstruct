/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator;

/**
 *
 * @author Sjaak Derksen
 */
public class EmployerMapper {

    public Employer fromDto(EmployerDto dto) {
        Employer employer = new Employer();
        employer.setName( dto.getName() );
        return employer;
    }

    public EmployerDto toDto(Employer employer) {
        EmployerDto dto = new EmployerDto();
        dto.setName( employer.getName() );
        return dto;
    }
}
