/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator;

import java.util.Date;

/**
 *
 * @author Sjaak Derksen
 */
public class Person2 extends Person {

    private SportsClub sportsClub;

    private Employer employer;

    public Person2(String firstName, String lastName, Date dateOfBirth, Address address) {
        super( firstName, lastName, dateOfBirth, address );
    }

    public SportsClub getSportsClub() {
        return sportsClub;
    }

    public void setSportsClub(SportsClub sportsClub) {
        this.sportsClub = sportsClub;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
