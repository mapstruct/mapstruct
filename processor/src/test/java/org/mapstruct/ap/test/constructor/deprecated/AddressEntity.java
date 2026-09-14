/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.deprecated;

/**
 * The shape that kapt generates for a Kotlin JPA entity since Kotlin 2.3.0.
 * <p>
 * For
 * <pre>
 * &#64;Entity class AddressEntity(val street: String, val city: String)
 * </pre>
 * the {@code no-arg} compiler plugin, applied by {@code kotlin("plugin.jpa")}, adds the parameterless constructor
 * that JPA requires. Until Kotlin 2.3.0 it was missing from the stubs (KT-53122), so MapStruct never saw it. The
 * generated constructor carries {@code @java.lang.Deprecated} and is not synthetic, which is exactly what javac
 * produces for the declaration below.
 * <p>
 * Since the properties of the entity are {@code val}, there are no setters: the declared constructor is the only way
 * to populate the object.
 *
 * @author Oleg Babichev
 */
public class AddressEntity {

    private final String street;
    private final String city;

    @Deprecated
    public AddressEntity() {
        this( null, null );
    }

    public AddressEntity(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }
}
