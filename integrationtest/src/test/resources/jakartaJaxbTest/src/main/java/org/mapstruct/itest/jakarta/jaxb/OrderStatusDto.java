/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jakarta.jaxb;

/**
 * @author Sjaak Derksen
 */
public enum OrderStatusDto {

    ORDERED( "small" ),
    PROCESSED( "medium" ),
    DELIVERED( "large" );
    private final String value;

    OrderStatusDto(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrderStatusDto fromValue(String v) {
        for ( OrderStatusDto c : OrderStatusDto.values() ) {
            if ( c.value.equals( v ) ) {
                return c;
            }
        }
        throw new IllegalArgumentException( v );
    }

}
