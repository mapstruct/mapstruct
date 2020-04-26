/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.mixed;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class PersonMixed {

    private final String name;
    private final int age;
    private final String job;
    private String city;
    private String address;
    private List<String> children;

    public PersonMixed(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        throw new RuntimeException( "Method is here only to verify that MapStruct won't use it" );
    }

    public int getAge() {
        return age;
    }

    public String getJob() {
        return job;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
