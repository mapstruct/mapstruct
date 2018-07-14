/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

public class RoofDto {
    private ColorDto color;
    private ExternalRoofType type;

    public ColorDto getColor() {
        return color;
    }

    public void setColor(ColorDto color) {
        this.color = color;
    }

    public ExternalRoofType getType() {
        return type;
    }

    public void setType(ExternalRoofType type) {
        this.type = type;
    }
}
