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

    private JSpecifyConstants() {
    }

    public static final String NULLABLE_FQN = "org.jspecify.annotations.Nullable";

    public static final String NON_NULL_FQN = "org.jspecify.annotations.NonNull";

    public static final String NULL_MARKED_FQN = "org.jspecify.annotations.NullMarked";

    public static final String NULL_UNMARKED_FQN = "org.jspecify.annotations.NullUnmarked";
}
