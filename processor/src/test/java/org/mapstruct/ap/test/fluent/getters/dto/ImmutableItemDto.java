/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.fluent.getters.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ImmutableItemDto extends ItemDto {

    private final String id;

    private ImmutableItemDto(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    public ImmutableItemDto withId(String value) {
        if ( Objects.equals( this.id, value ) ) {
            return this;
        }
        return new ImmutableItemDto( value );
    }

    @Override
    public boolean equals(Object another) {
        if ( this == another ) {
            return true;
        }
        return another instanceof ImmutableItemDto && id.equals( ( (ImmutableItemDto) another ).id );
    }

    @Override
    public int hashCode() {
        int h = 5381;
        h += ( h << 5 ) + id.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return "ItemDTO{id=" + id + "}";
    }

    public static ImmutableItemDto copyOf(ItemDto instance) {
        if ( instance instanceof ImmutableItemDto ) {
            return (ImmutableItemDto) instance;
        }
        return ImmutableItemDto.builder().from( instance ).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private static final long INIT_BIT_ID = 0x1L;
        private long initBits = 0x1L;

        private String id;

        private Builder() {
        }

        public Builder from(ItemDto instance) {
            id( instance.id() );
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            initBits &= ~INIT_BIT_ID;
            return this;
        }

        public ImmutableItemDto build() {
            if ( initBits != 0 ) {
                throw new IllegalStateException( formatRequiredAttributesMessage() );
            }
            return new ImmutableItemDto( id );
        }

        private String formatRequiredAttributesMessage() {
            List<String> attributes = new ArrayList<>();
            if ( ( initBits & INIT_BIT_ID ) != 0 ) {
                attributes.add( "id" );
            }
            return "Cannot build ItemDTO, some of required attributes are not set " + attributes;
        }

    }

}
