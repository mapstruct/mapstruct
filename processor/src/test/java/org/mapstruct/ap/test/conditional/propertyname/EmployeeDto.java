/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.propertyname;

import java.util.List;

/**
 * @author Nikola Ivačič
 */
public class EmployeeDto implements DomainModel {

    private String firstName;
    private String lastName;
    private String title;
    private String originCountry;
    private boolean active;
    private int age;

    private EmployeeDto boss;

    private AddressDto primaryAddress;
    private List<AddressDto> originAddresses;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public EmployeeDto getBoss() {
        return boss;
    }

    public void setBoss(EmployeeDto boss) {
        this.boss = boss;
    }

    public AddressDto getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(AddressDto primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public List<AddressDto> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(List<AddressDto> originAddresses) {
        this.originAddresses = originAddresses;
    }
}
