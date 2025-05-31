/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.kotlin;

import kotlin.Metadata;

/**
 * A deliberately constructed, fake Kotlin data class.
 */
@SuppressWarnings("unused")
@Metadata
public class PersonDataClass {

    private final String name;
    private final int age;
    private final String job;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getJob() {
        return job;
    }

    public PersonDataClass(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public PersonDataClass(String name, int age, String job, String email) {
        this( name, age, job );
    }

    public PersonDataClass() {
        this( null, 0, null );
    }

    public PersonDataClass(String name, int age) {
        this( name, age, null );
    }

    public String component1() {
        return name;
    }

    public int component2() {
        return age;
    }

    public String component3() {
        return job;
    }
}
