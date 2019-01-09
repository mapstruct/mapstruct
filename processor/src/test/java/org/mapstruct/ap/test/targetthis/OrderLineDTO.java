/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

public class OrderLineDTO {

    public int getLine() {
        return line;
    }

    public void setLine( int line ) {
        this.line = line;
    }

    private int line;
}
