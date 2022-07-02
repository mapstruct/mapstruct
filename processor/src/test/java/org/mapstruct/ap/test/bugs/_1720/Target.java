/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1720;

import java.util.Objects;

public class Target {

    private int id;
    private String fullName;
    private int value;

    public Target() {
    }

    public Target(int id, String fullName, int value) {
        this.id = id;
        this.fullName = fullName;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Target that = (Target) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }

}
