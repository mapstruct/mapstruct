/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper holding constants for working with Joda-Time.
 *
 * @author Timo Eckhardt
 */
public final class JodaTimeConstants {

    private JodaTimeConstants() {
    }

    public static final String DATE_TIME_FQN = "org.joda.time.DateTime";

    public static final String LOCAL_DATE_TIME_FQN = "org.joda.time.LocalDateTime";

    public static final String LOCAL_DATE_FQN = "org.joda.time.LocalDate";

    public static final String LOCAL_TIME_FQN = "org.joda.time.LocalTime";

    public static final String DATE_TIME_FORMAT_FQN = "org.joda.time.format.DateTimeFormat";

    public static final String DATE_TIME_ZONE_FQN = "org.joda.time.DateTimeZone";

    public static final String DATE_TIME_FORMAT = "LL";
}
