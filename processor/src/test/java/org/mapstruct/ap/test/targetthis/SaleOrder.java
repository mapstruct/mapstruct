/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

public class SaleOrder extends OrderEntity {
    public String getNumber() {
        return number;
    }

    public void setNumber( String number ) {
        this.number = number;
    }

    private String number;

}
