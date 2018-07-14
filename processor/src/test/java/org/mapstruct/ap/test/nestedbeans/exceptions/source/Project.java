/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exceptions.source;

import java.util.List;

/**
 * @author Filip Hrisafov
 * @author Darren Rambaud
 */
public class Project {

    private List<Developer> assignedDevelopers;

    public List<Developer> getAssignedDevelopers() {
        return assignedDevelopers;
    }

    public void setAssignedDevelopers(List<Developer> assignedDevelopers) {
        this.assignedDevelopers = assignedDevelopers;
    }
}
