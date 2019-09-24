/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.presencecheck;

import java.util.List;

/**
 * @author Kirill Baurchanu
 */
public class User {
    private String name;
    private ContactInformation contacts;
    private List<String> roles;

    public String getName() {
        return name;
    }

    public boolean hasNoName() {
        return name == null || name.isEmpty();
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactInformation getContacts() {
        return contacts;
    }

    public boolean hasNoContacts() {
        return contacts == null;
    }

    public void setContacts(ContactInformation contacts) {
        this.contacts = contacts;
    }

    public List<String> getRoles() {
        return roles;
    }

    public boolean hasNoRoles() {
        return roles == null || roles.isEmpty();
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static class ContactInformation {
        private String email;
        private String address;

        public String getEmail() {
            return email;
        }

        public boolean hasNoEmail() {
            return email == null || email.isEmpty();
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public boolean hasNoAddress() {
            return address == null || address.isEmpty();
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
