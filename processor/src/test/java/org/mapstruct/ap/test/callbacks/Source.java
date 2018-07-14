/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks;

/**
 * @author Andreas Gudian
 */
public class Source {
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
        Source other = (Source) obj;
        if ( foo == null ) {
            if ( other.foo != null ) {
                return false;
            }
        }
        else if ( !foo.equals( other.foo ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Source [foo=" + foo + "]";
    }
}
