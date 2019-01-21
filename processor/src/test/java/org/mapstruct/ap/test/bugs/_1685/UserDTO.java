/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1685;

public class UserDTO {
    private String name;
    private ContactDataDTO contactDataDTO;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactDataDTO getContactDataDTO() {
        return contactDataDTO;
    }

    public void setContactDataDTO(ContactDataDTO contactDataDTO) {
        this.contactDataDTO = contactDataDTO;
    }
}
