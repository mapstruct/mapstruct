/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection;

/**
 * @author Andreas Gudian
 *
 */
public class StringHolder {
    private final String string;

    public StringHolder(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( string == null ) ? 0 : string.hashCode() );
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
        StringHolder other = (StringHolder) obj;
        if ( string == null ) {
            if ( other.string != null ) {
                return false;
            }
        }
        else if ( !string.equals( other.string ) ) {
            return false;
        }
        return true;
    }
}
