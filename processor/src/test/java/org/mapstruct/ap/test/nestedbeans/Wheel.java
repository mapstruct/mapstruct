/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

public class Wheel {
    private boolean front;
    private boolean right;

    public Wheel() {
    }

    public Wheel(boolean front, boolean right) {
        this.front = front;
        this.right = right;
    }

    public boolean isFront() {
        return front;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public Wheel left() {
        return new Wheel( this.front, false );
    }

    public Wheel right() {
        return new Wheel( this.front, true );
    }

    public Wheel front() {
        return new Wheel( true, this.right );
    }

    public Wheel rear() {
        return new Wheel( false, this.right );
    }

    @Override
    public boolean equals(Object o) {

        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Wheel wheel = (Wheel) o;

        if ( front != wheel.front ) {
            return false;
        }
        return right == wheel.right;

    }

    @Override
    public int hashCode() {
        int result = ( front ? 1 : 0 );
        result = 31 * result + ( right ? 1 : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "Wheel{" +
            "front=" + front +
            ", right=" + right +
            '}';
    }

}
