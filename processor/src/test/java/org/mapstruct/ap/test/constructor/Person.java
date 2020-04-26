/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Person {

    private final String name;
    private final int age;
    private final String job;
    private final String city;
    private final String address;
    private final List<String> children;

    public Person(String name, int age, String job, String city, String address,
                  List<String> children) {
        this.name = name;
        this.age = age;
        this.job = job;
        this.city = city;
        this.address = address;
        this.children = children;
    }

    public String getName() {
        return name;
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

    public String getAddress() {
        return address;
    }

    public List<String> getChildren() {
        return children;
    }
}
