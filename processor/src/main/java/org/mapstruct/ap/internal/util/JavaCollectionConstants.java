/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper holding Java collections full qualified class names for conversion registration,
 * to achieve Java compatibility.
 *
 * @author Cause Chung
 */
public final class JavaCollectionConstants {
    public static final String SEQUENCED_MAP_FQN = "java.util.SequencedMap";
    public static final String SEQUENCED_SET_FQN = "java.util.SequencedSet";

    private JavaCollectionConstants() {

    }
}
