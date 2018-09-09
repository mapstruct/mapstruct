/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1596.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable implementation of {@link ItemDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableItemDTO.builder()}.
 */
@SuppressWarnings({ "all" })
public final class ImmutableItemDTO extends ItemDTO {
    private final String id;

    private ImmutableItemDTO(String id) {
        this.id = id;
    }

    /**
     * @return The value of the {@code id} attribute
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Copy the current immutable object by setting a value for the {@link ItemDTO#getId() id} attribute.
     * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
     *
     * @param value A new value for id
     *
     * @return A modified copy of the {@code this} object
     */
    public final ImmutableItemDTO withId(String value) {
        if ( this.id == value ) {
            return this;
        }
        return new ImmutableItemDTO( value );
    }

    /**
     * This instance is equal to all instances of {@code ImmutableItemDTO} that have equal attribute values.
     *
     * @return {@code true} if {@code this} is equal to {@code another} instance
     */
    @Override
    public boolean equals(Object another) {
        if ( this == another ) {
            return true;
        }
        return another instanceof ImmutableItemDTO
            && equalTo( (ImmutableItemDTO) another );
    }

    private boolean equalTo(ImmutableItemDTO another) {
        return id.equals( another.id );
    }

    /**
     * Computes a hash code from attributes: {@code id}.
     *
     * @return hashCode value
     */
    @Override
    public int hashCode() {
        int h = 5381;
        h += ( h << 5 ) + id.hashCode();
        return h;
    }

    /**
     * Prints the immutable value {@code ItemDTO} with attribute values.
     *
     * @return A string representation of the value
     */
    @Override
    public String toString() {
        return "ItemDTO{"
            + "id=" + id
            + "}";
    }

    /**
     * Creates an immutable copy of a {@link ItemDTO} value.
     * Uses accessors to get values to initialize the new immutable instance.
     * If an instance is already immutable, it is returned as is.
     *
     * @param instance The instance to copy
     *
     * @return A copied immutable ItemDTO instance
     */
    public static ImmutableItemDTO copyOf(ItemDTO instance) {
        if ( instance instanceof ImmutableItemDTO ) {
            return (ImmutableItemDTO) instance;
        }
        return ImmutableItemDTO.builder()
            .from( instance )
            .build();
    }

    /**
     * Creates a builder for {@link ImmutableItemDTO ImmutableItemDTO}.
     *
     * @return A new ImmutableItemDTO builder
     */
    public static ImmutableItemDTO.Builder builder() {
        return new ImmutableItemDTO.Builder();
    }

    /**
     * Builds instances of type {@link ImmutableItemDTO ImmutableItemDTO}.
     * Initialize attributes and then invoke the {@link #build()} method to create an
     * immutable instance.
     * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
     * but instead used immediately to create instances.</em>
     */
    public static final class Builder {
        private static final long INIT_BIT_ID = 0x1L;
        private long initBits = 0x1L;

        private String id;

        private Builder() {
        }

        /**
         * Fill a builder with attribute values from the provided {@code ItemDTO} instance.
         * Regular attribute values will be replaced with those from the given instance.
         * Absent optional values will not replace present values.
         *
         * @param instance The instance from which to copy values
         *
         * @return {@code this} builder for use in a chained invocation
         */
        public final Builder from(ItemDTO instance) {
            id( instance.getId() );
            return this;
        }

        /**
         * Initializes the value for the {@link ItemDTO#getId() id} attribute.
         *
         * @param id The value for id
         *
         * @return {@code this} builder for use in a chained invocation
         */
        public final Builder id(String id) {
            this.id = id;
            initBits &= ~INIT_BIT_ID;
            return this;
        }

        /**
         * Builds a new {@link ImmutableItemDTO ImmutableItemDTO}.
         *
         * @return An immutable instance of ItemDTO
         *
         * @throws java.lang.IllegalStateException if any required attributes are missing
         */
        public ImmutableItemDTO build() {
            if ( initBits != 0 ) {
                throw new IllegalStateException( formatRequiredAttributesMessage() );
            }
            return new ImmutableItemDTO( id );
        }

        private String formatRequiredAttributesMessage() {
            List<String> attributes = new ArrayList<String>();
            if ( ( initBits & INIT_BIT_ID ) != 0 ) {
                attributes.add( "id" );
            }
            return "Cannot build ItemDTO, some of required attributes are not set " + attributes;
        }
    }
}
