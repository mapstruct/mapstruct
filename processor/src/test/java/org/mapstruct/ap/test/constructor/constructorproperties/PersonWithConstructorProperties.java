/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.constructorproperties;

import java.util.List;

import org.mapstruct.ap.test.constructor.ConstructorProperties;

/**
 * @author Filip Hrisafov
 */
public class PersonWithConstructorProperties {

    private final String name;
    private final int age;
    private final String job;
    private final String city;
    private final String address;
    private final List<String> children;

    @ConstructorProperties({"name", "age", "job", "city", "address", "children"})
    public PersonWithConstructorProperties(String var1, int var2, String var3, String var4, String var5,
                  List<String> var6) {
        this.name = var1;
        this.age = var2;
        this.job = var3;
        this.city = var4;
        this.address = var5;
        this.children = var6;
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
