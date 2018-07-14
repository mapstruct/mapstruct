/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exceptions._target;

import java.util.List;

/**
 * @author Filip Hrisafov
 * @author Darren Rambaud
 */
public class ProjectDto {

    private List<DeveloperDto> assignedDevelopers;

    public List<DeveloperDto> getAssignedDevelopers() {
        return assignedDevelopers;
    }

    public void setAssignedDevelopers(List<DeveloperDto> assignedDevelopers) {
        this.assignedDevelopers = assignedDevelopers;
    }
}
