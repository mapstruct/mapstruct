/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluepropertymapping;

import java.util.List;
import java.util.Map;

public class UserDTO {

    private HomeDTO homeDTO;
    private List<String> details;

    private Map<String, String> otherDetails;

    public HomeDTO getHomeDTO() {
        return homeDTO;
    }

    public void setHomeDTO(HomeDTO homeDTO) {
        this.homeDTO = homeDTO;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public Map<String, String> getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(Map<String, String> otherDetails) {
        this.otherDetails = otherDetails;
    }
}
