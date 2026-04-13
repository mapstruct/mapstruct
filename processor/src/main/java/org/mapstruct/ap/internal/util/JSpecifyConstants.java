/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper holding constants for working with JSpecify null annotations.
 *
 * @author Filip Hrisafov
 */
public final class JSpecifyConstants {

    /**
     * Fully qualified name of {@code org.jspecify.annotations.Nullable}.
     */
    public static final String NULLABLE_FQN = "org.jspecify.annotations.Nullable";

    /**
     * Fully qualified name of {@code org.jspecify.annotations.NonNull}.
     */
    public static final String NON_NULL_FQN = "org.jspecify.annotations.NonNull";

    /**
     * Fully qualified name of {@code org.jspecify.annotations.NullMarked}. Its presence on an
     * element (or any enclosing element) means unannotated types in that scope are effectively
     * {@code @NonNull}.
     */
    public static final String NULL_MARKED_FQN = "org.jspecify.annotations.NullMarked";

    /**
     * Fully qualified name of {@code org.jspecify.annotations.NullUnmarked}. Its presence on an
     * element (or an enclosing element closer than a {@code @NullMarked} one) reverts the scope
     * back to unknown nullability.
     */
    public static final String NULL_UNMARKED_FQN = "org.jspecify.annotations.NullUnmarked";

    private JSpecifyConstants() {
    }
}
