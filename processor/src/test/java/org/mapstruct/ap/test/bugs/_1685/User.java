/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1685;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private Integer phone;
    private String address;
    private List<String> preferences = new ArrayList<>(  );

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addPreference(String preference) {
        preferences.add( preference );
    }

    public List<String> getPreferences() {
        return preferences;
    }
}


