// CHECKSTYLE:OFF
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.immutables.value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Minimal stub of {@code org.immutables.value.Value} for use in MapStruct processor tests.
 * Only the annotations referenced by {@link org.mapstruct.ap.spi.ImmutablesBuilderProvider} are declared here.
 */
public @interface Value {

    /**
     * Stub for {@code @Value.Immutable}. Its presence on the classpath activates
     * {@link org.mapstruct.ap.spi.ImmutablesBuilderProvider} as the default builder provider.
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Immutable {
    }

    /**
     * Stub for {@code @Value.Builder}. Used by {@link org.mapstruct.ap.spi.ImmutablesBuilderProvider} to detect
     * Immutables-generated record builders.
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Builder {
    }
}
