/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.nestedbeans.exceptions._target;

import java.util.List;

/**
 * @author Filip Hrisafov
 * @author Darren Rambaud
 */
public class ProjectDto {
    private Long id;

    private String name;

    private Integer priority;

    private List<DeveloperDto> assignedDevelopers;

    private Double burnDownRate;

    private Integer hoursWasted;

    private Long potentialCashFlow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<DeveloperDto> getAssignedDevelopers() {
        return assignedDevelopers;
    }

    public void setAssignedDevelopers(
        List<DeveloperDto> assignedDevelopers) {
        this.assignedDevelopers = assignedDevelopers;
    }

    public Double getBurnDownRate() {
        return burnDownRate;
    }

    public void setBurnDownRate(Double burnDownRate) {
        this.burnDownRate = burnDownRate;
    }

    public Integer getHoursWasted() {
        return hoursWasted;
    }

    public void setHoursWasted(Integer hoursWasted) {
        this.hoursWasted = hoursWasted;
    }

    public Long getPotentialCashFlow() {
        return potentialCashFlow;
    }

    public void setPotentialCashFlow(Long potentialCashFlow) {
        this.potentialCashFlow = potentialCashFlow;
    }
}
