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
package org.mapstruct.ap.test.conversion.java8time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 *
 */
public class Source {

    private ZonedDateTime zonedDateTime;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private LocalTime localTime;

    private ZonedDateTime forCalendarConversion;

    private ZonedDateTime forDateConversionWithZonedDateTime;

    private LocalDateTime forDateConversionWithLocalDateTime;

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime dateTime) {
        this.zonedDateTime = dateTime;
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

    public ZonedDateTime getForCalendarConversion() {
        return forCalendarConversion;
    }

    public void setForCalendarConversion(ZonedDateTime forCalendarConversion) {
        this.forCalendarConversion = forCalendarConversion;
    }

    public ZonedDateTime getForDateConversionWithZonedDateTime() {
        return forDateConversionWithZonedDateTime;
    }

    public void setForDateConversionWithZonedDateTime(ZonedDateTime forDateConversionWithZonedDateTime) {
        this.forDateConversionWithZonedDateTime = forDateConversionWithZonedDateTime;
    }

    public LocalDateTime getForDateConversionWithLocalDateTime() {
        return forDateConversionWithLocalDateTime;
    }

    public void setForDateConversionWithLocalDateTime(LocalDateTime forDateConversionWithLocalDateTime) {
        this.forDateConversionWithLocalDateTime = forDateConversionWithLocalDateTime;
    }
}
