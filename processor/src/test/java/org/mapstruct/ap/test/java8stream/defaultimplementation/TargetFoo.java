/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

public class TargetFoo implements Comparable<TargetFoo> {

    private String name;

    public TargetFoo() {
    }

    public TargetFoo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        TargetFoo other = (TargetFoo) obj;
        if ( name == null ) {
            return other.name == null;
        }
        else {
            return name.equals( other.name );
        }
    }

    @Override
    public int compareTo(TargetFoo o) {
        return getName().compareTo( o.getName() );
    }
}
