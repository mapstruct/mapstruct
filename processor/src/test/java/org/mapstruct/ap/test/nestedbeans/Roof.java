/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

public class Roof {
    private int color;
    private RoofType type;

    public Roof() {
    }

    public Roof(int color, RoofType type) {
        this.color = color;
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Roof{" +
            "color='" + color + '\'' +
            "type='" + type + '\'' +
            '}';
    }

    public RoofType getType() {
        return type;
    }

    public Roof setType(RoofType type) {
        this.type = type;
        return this;
    }
}
