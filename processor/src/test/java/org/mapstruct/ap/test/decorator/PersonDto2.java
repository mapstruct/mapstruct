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
public class PersonDto2 extends PersonDto {

    private SportsClubDto sportsClub;

    private EmployerDto employer;

    public SportsClubDto getSportsClub() {
        return sportsClub;
    }

    public void setSportsClub(SportsClubDto sportsClub) {
        this.sportsClub = sportsClub;
    }

    public EmployerDto getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDto employer) {
        this.employer = employer;
    }
}
