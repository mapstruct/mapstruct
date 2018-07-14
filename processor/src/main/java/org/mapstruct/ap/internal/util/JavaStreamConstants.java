/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper holding Java Stream full qualified class names for conversion registration
 *
 * @author Filip Hrisafov
 */
public final class JavaStreamConstants {

    public static final String STREAM_FQN = "java.util.stream.Stream";
    public static final String COLLECTORS_FQN = "java.util.stream.Collectors";
    public static final String STREAM_SUPPORT_FQN = "java.util.stream.StreamSupport";
    public static final String OPTIONAL_FQN = "java.util.Optional";
    public static final String FUNCTION_FQN = "java.util.function.Function";

    private JavaStreamConstants() {
    }
}
