/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

public class Roof {
    private Color color;
    private RoofType type;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public RoofType getType() {
        return type;
    }

    public Roof setType(RoofType type) {
        this.type = type;
        return this;
    }
}
