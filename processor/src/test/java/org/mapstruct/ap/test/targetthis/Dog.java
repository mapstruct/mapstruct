/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

public class Dog extends Animal {
    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    private String type;

    public String getColor() {
        return color;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    private String color;
}
