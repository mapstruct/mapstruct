/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import java.util.List;

/**
 * Target Property (TargetPropertyModel) test entities
 *
 * @author Nikola Ivačič
 */
public interface TargetPropertyNameModel {

    class Employee implements TargetPropertyNameModel {

        private String firstName;
        private String lastName;
        private String title;
        private String country;
        private boolean active;
        private int age;

        private Employee boss;

        private Address primaryAddress;

        private List<Address> addresses;

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
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

        public Employee getBoss() {
            return boss;
        }

        public void setBoss(Employee boss) {
            this.boss = boss;
        }

        public Address getPrimaryAddress() {
            return primaryAddress;
        }

        public void setPrimaryAddress(Address primaryAddress) {
            this.primaryAddress = primaryAddress;
        }

        public List<Address> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<Address> addresses) {
            this.addresses = addresses;
        }
    }

    class Address implements TargetPropertyNameModel {
        private String street;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }
    }

    class EmployeeDto implements TargetPropertyNameModel {

        private String firstName;
        private String lastName;
        private String title;
        private String country;
        private boolean active;
        private int age;

        private EmployeeDto boss;

        private AddressDto primaryAddress;
        private List<AddressDto> addresses;

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
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

        public List<AddressDto> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<AddressDto> addresses) {
            this.addresses = addresses;
        }
    }

    class AddressDto implements TargetPropertyNameModel {
        private String street;

        public AddressDto() {
        }

        public AddressDto(String street) {
            this.street = street;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }
    }
}
