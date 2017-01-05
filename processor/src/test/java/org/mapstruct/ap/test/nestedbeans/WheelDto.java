/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.nestedbeans;

public class WheelDto {
    private boolean front;
    private boolean right;

    public WheelDto() {
    }

    public WheelDto(boolean front, boolean right) {
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

    @Override
    public boolean equals(Object o) {

        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        WheelDto wheel = (WheelDto) o;

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
        return "Wheel{" + ( front ? "front" : "rear" ) + ";" + ( right ? "right" : "left" ) + '}';
    }

}
