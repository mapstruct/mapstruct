/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks;

/**
 * @author Andreas Gudian
 */
public class Target {
    private String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( foo == null ) ? 0 : foo.hashCode() );
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
        Target other = (Target) obj;
        if ( foo == null ) {
            return other.foo == null;
        }
        else {
            return foo.equals( other.foo );
        }
    }

    @Override
    public String toString() {
        return "Target [foo=" + foo + "]";
    }
}
