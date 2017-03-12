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

public class RoofDto {
    private String color;
    private ExternalRoofType type;

    public RoofDto() {
    }

    public RoofDto(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ExternalRoofType getType() {
        return type;
    }

    public RoofDto setType(ExternalRoofType type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        RoofDto roofDto = (RoofDto) o;

        if ( type != ( (RoofDto) o ).type ) {
            return false;
        }

        return color != null ? color.equals( roofDto.color ) : roofDto.color == null;

    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + ( type != null ? type.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "RoofDto{" +
            "color='" + color + '\'' +
            "type='" + type + '\'' +
            '}';
    }
}
