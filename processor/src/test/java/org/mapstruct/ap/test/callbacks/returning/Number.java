/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

/**
 * @author Pascal Grün
 */
public class Number {
    private int number;

    public Number() {
        this( 0 );
    }

    public Number(int number) {
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return 31 + number;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null || getClass() != obj.getClass() ) {
            return false;
        }
        Number other = (Number) obj;
        return number == other.number;
    }

    @Override
    public String toString() {
        return "Number[number=" + number + "]";
    }
}
