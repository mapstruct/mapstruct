/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper holding Java time full qualified class names for conversion registration
 */
public final class JavaTimeConstants {

    public static final String ZONED_DATE_TIME_FQN = "java.time.ZonedDateTime";
    public static final String ZONE_OFFSET_FQN = "java.time.ZoneOffset";
    public static final String ZONE_ID_FQN = "java.time.ZoneId";

    public static final String LOCAL_DATE_TIME_FQN = "java.time.LocalDateTime";
    public static final String LOCAL_DATE_FQN = "java.time.LocalDate";
    public static final String LOCAL_TIME_FQN = "java.time.LocalTime";

    public static final String DATE_TIME_FORMATTER_FQN = "java.time.format.DateTimeFormatter";

    private JavaTimeConstants() {
    }
}
