/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.Objects;

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

        return Objects.equals( color, roofDto.color );

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
