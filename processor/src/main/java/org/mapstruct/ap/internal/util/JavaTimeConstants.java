/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.util;

/**
 * Helper holding Java time full qualified class names for conversion registration
 */
public final class JavaTimeConstants {

    public static final String ZONED_DATE_TIME_FQN = "java.time.ZonedDateTime";
    public static final String LOCAL_DATE_TIME_FQN = "java.time.LocalDateTime";
    public static final String LOCAL_DATE_FQN = "java.time.LocalDate";
    public static final String LOCAL_TIME_FQN = "java.time.LocalTime";
    public static final String DATE_TIME_FORMATTER_FQN = "java.time.format.DateTimeFormatter";

    private JavaTimeConstants() {
    }
}
