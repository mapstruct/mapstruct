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
package org.mapstruct.ap.test.conversion.jodatime;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class Source {

    private DateTime dateTime;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private LocalTime localTime;

    private DateTime dateTimeForDateConversion;

    private DateTime dateTimeForCalendarConversion;

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public DateTime getDateTimeForDateConversion() {
        return dateTimeForDateConversion;
    }

    public void setDateTimeForDateConversion(DateTime dateTimeForDateConversion) {
        this.dateTimeForDateConversion = dateTimeForDateConversion;
    }

    public DateTime getDateTimeForCalendarConversion() {
        return dateTimeForCalendarConversion;
    }

    public void setDateTimeForCalendarConversion(DateTime dateTimeForCalendarConversion) {
        this.dateTimeForCalendarConversion = dateTimeForCalendarConversion;
    }
}
