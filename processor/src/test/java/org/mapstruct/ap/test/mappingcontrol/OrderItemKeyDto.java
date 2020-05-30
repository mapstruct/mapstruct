/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

/**
 * @author Sjaak Derksen
 */
public class OrderItemKeyDto {

    private long stockNumber;

    public long getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(long stockNumber) {
        this.stockNumber = stockNumber;
    }
}
