/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3089.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable implementation of {@link ItemDTO}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableItemDTO.builder()}.
 *
 * @author Oliver Erhart
 */
public final class ImmutableItemDTO extends ItemDTO {
    private final String id;
    private final Map<String, String> attributes;

    private ImmutableItemDTO(String id, Map<String, String> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    /**
     * @return The value of the {@code id} attribute
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @return The value of the {@code attributes} attribute
     */
    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Copy the current immutable object by setting a value for the {@link ItemDTO#getId() id} attribute.
     * An equals check used to prevent copying of the same value by returning {@code this}.
     *
     * @param value A new value for id
     * @return A modified copy of the {@code this} object
     */
    public ImmutableItemDTO withId(String value) {
        String newValue = Objects.requireNonNull( value, "id" );
        if ( this.id.equals( newValue ) ) {
            return this;
        }
        return new ImmutableItemDTO( newValue, this.attributes );
    }

    /**
     * Copy the current immutable object by replacing the {@link ItemDTO#getAttributes() attributes} map with
     * the specified map.
     * Nulls are not permitted as keys or values.
     * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
     *
     * @param entries The entries to be added to the attributes map
     * @return A modified copy of {@code this} object
     */
    public ImmutableItemDTO withAttributes(Map<String, ? extends String> entries) {
        if ( this.attributes == entries ) {
            return this;
        }
        Map<String, String> newValue = createUnmodifiableMap( true, false, entries );
        return new ImmutableItemDTO( this.id, newValue );
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
        return id.equals( another.id )
            && attributes.equals( another.attributes );
    }

    /**
     * Computes a hash code from attributes: {@code id}, {@code attributes}.
     *
     * @return hashCode value
     */
    @Override
    public int hashCode() {
        int h = 5381;
        h += ( h << 5 ) + id.hashCode();
        h += ( h << 5 ) + attributes.hashCode();
        return h;
    }

    /**
     * Prints the immutable value {@code Item} with attribute values.
     *
     * @return A string representation of the value
     */
    @Override
    public String toString() {
        return "Item{"
            + "id=" + id
            + ", attributes=" + attributes
            + "}";
    }

    /**
     * Creates an immutable copy of a {@link ItemDTO} value.
     * Uses accessors to get values to initialize the new immutable instance.
     * If an instance is already immutable, it is returned as is.
     *
     * @param instance The instance to copy
     * @return A copied immutable Item instance
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
     * <pre>
     * ImmutableItemDTO.builder()
     *    .id(String) // required {@link ItemDTO#getId() id}
     *    .putAttributes|putAllAttributes(String =&gt; String) // {@link ItemDTO#getAttributes() attributes} mappings
     *    .build();
     * </pre>
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
    public static class Builder {
        private static final long INIT_BIT_ID = 0x1L;
        private long initBits = 0x1L;

        private String id;
        private Map<String, String> attributes = new LinkedHashMap<String, String>();

        public Builder() {
        }

        /**
         * Fill a builder with attribute values from the provided {@code Item} instance.
         * Regular attribute values will be replaced with those from the given instance.
         * Absent optional values will not replace present values.
         * Collection elements and entries will be added, not replaced.
         *
         * @param instance The instance from which to copy values
         * @return {@code this} builder for use in a chained invocation
         */
        public final ImmutableItemDTO.Builder from(ItemDTO instance) {
            Objects.requireNonNull( instance, "instance" );
            id( instance.getId() );
            putAllAttributes( instance.getAttributes() );
            return this;
        }

        /**
         * Initializes the value for the {@link ItemDTO#getId() id} attribute.
         *
         * @param id The value for id
         * @return {@code this} builder for use in a chained invocation
         */
        public final ImmutableItemDTO.Builder id(String id) {
            this.id = Objects.requireNonNull( id, "id" );
            initBits &= ~INIT_BIT_ID;
            return this;
        }

        /**
         * Put one entry to the {@link ItemDTO#getAttributes() attributes} map.
         *
         * @param key The key in the attributes map
         * @param value The associated value in the attributes map
         * @return {@code this} builder for use in a chained invocation
         */
        public final ImmutableItemDTO.Builder putAttributes(String key, String value) {
            this.attributes.put(
                Objects.requireNonNull( key, "attributes key" ),
                Objects.requireNonNull( value, "attributes value" )
            );
            return this;
        }

        /**
         * Put one entry to the {@link ItemDTO#getAttributes() attributes} map. Nulls are not permitted
         *
         * @param entry The key and value entry
         * @return {@code this} builder for use in a chained invocation
         */
        public final ImmutableItemDTO.Builder putAttributes(Map.Entry<String, ? extends String> entry) {
            String k = entry.getKey();
            String v = entry.getValue();
            this.attributes.put(
                Objects.requireNonNull( k, "attributes key" ),
                Objects.requireNonNull( v, "attributes value" )
            );
            return this;
        }

        /**
         * Sets or replaces all mappings from the specified map as entries for the {@link ItemDTO#getAttributes()
         * attributes} map. Nulls are not permitted
         *
         * @param entries The entries that will be added to the attributes map
         * @return {@code this} builder for use in a chained invocation
         */
        public final ImmutableItemDTO.Builder attributes(Map<String, ? extends String> entries) {
            this.attributes.clear();
            return putAllAttributes( entries );
        }

        /**
         * Put all mappings from the specified map as entries to {@link ItemDTO#getAttributes() attributes} map.
         * Nulls are not permitted
         *
         * @param entries The entries that will be added to the attributes map
         * @return {@code this} builder for use in a chained invocation
         */
        public final ImmutableItemDTO.Builder putAllAttributes(Map<String, ? extends String> entries) {
            for ( Map.Entry<String, ? extends String> e : entries.entrySet() ) {
                String k = e.getKey();
                String v = e.getValue();
                this.attributes.put(
                    Objects.requireNonNull( k, "attributes key" ),
                    Objects.requireNonNull( v, "attributes value" )
                );
            }
            return this;
        }

        /**
         * Builds a new {@link ImmutableItemDTO ImmutableItemDTO}.
         *
         * @return An immutable instance of Item
         * @throws java.lang.IllegalStateException if any required attributes are missing
         */
        public ImmutableItemDTO build() {
            if ( initBits != 0 ) {
                throw new IllegalStateException( formatRequiredAttributesMessage() );
            }
            return new ImmutableItemDTO( id, createUnmodifiableMap( false, false, attributes ) );
        }

        private String formatRequiredAttributesMessage() {
            List<String> attributes = new ArrayList<>();
            if ( ( initBits & INIT_BIT_ID ) != 0 ) {
                attributes.add( "id" );
            }
            return "Cannot build Item, some of required attributes are not set " + attributes;
        }
    }

    @SuppressWarnings( "checkstyle:AvoidNestedBlocks" )
    private static <K, V> Map<K, V> createUnmodifiableMap(boolean checkNulls, boolean skipNulls,
                                                          Map<? extends K, ? extends V> map) {
        switch ( map.size() ) {
            case 0:
                return Collections.emptyMap();
            case 1: {
                Map.Entry<? extends K, ? extends V> e = map.entrySet().iterator().next();
                K k = e.getKey();
                V v = e.getValue();
                if ( checkNulls ) {
                    Objects.requireNonNull( k, "key" );
                    Objects.requireNonNull( v, "value" );
                }
                if ( skipNulls && ( k == null || v == null ) ) {
                    return Collections.emptyMap();
                }
                return Collections.singletonMap( k, v );
            }
            default: {
                Map<K, V> linkedMap = new LinkedHashMap<>( map.size() );
                if ( skipNulls || checkNulls ) {
                    for ( Map.Entry<? extends K, ? extends V> e : map.entrySet() ) {
                        K k = e.getKey();
                        V v = e.getValue();
                        if ( skipNulls ) {
                            if ( k == null || v == null ) {
                                continue;
                            }
                        }
                        else if ( checkNulls ) {
                            Objects.requireNonNull( k, "key" );
                            Objects.requireNonNull( v, "value" );
                        }
                        linkedMap.put( k, v );
                    }
                }
                else {
                    linkedMap.putAll( map );
                }
                return Collections.unmodifiableMap( linkedMap );
            }
        }
    }
}
