/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.defaultannotated;

import org.mapstruct.ap.test.constructor.Default;

/**
 * @author Filip Hrisafov
 */
public class PersonWithDefaultAnnotatedConstructor {

    private final String name;
    private final int age;

    public PersonWithDefaultAnnotatedConstructor(String name) {
        this( name, -1 );
    }

    @Default
    public PersonWithDefaultAnnotatedConstructor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
