/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.other;

import java.util.Objects;

public class RoofDto {
    private String color;

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

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        RoofDto roofDto = (RoofDto) o;

        return Objects.equals( color, roofDto.color );

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RoofDto{" +
            "color='" + color + '\'' +
            '}';
    }

}
