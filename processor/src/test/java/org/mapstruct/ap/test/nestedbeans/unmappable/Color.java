/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

public class Color {
    private String cmyk;

    public Color() {
    }

    public Color(String cmyk) {
        this.cmyk = cmyk;
    }

    public String getCmyk() {
        return cmyk;
    }

    public void setCmyk(String cmyk) {
        this.cmyk = cmyk;
    }
}
