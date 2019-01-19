package org.mapstruct.ap.test.bugs.nesting;

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