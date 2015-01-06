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

import java.util.Calendar;
import java.util.Date;

public class Target {

    private String dateTime;

    private String localDateTime;

    private String localDate;

    private String localTime;

    private Date dateTimeForDateConversion;

    private Calendar dateTimeForCalendarConversion;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public Date getDateTimeForDateConversion() {
        return dateTimeForDateConversion;
    }

    public void setDateTimeForDateConversion(Date dateTimeForDateConversion) {
        this.dateTimeForDateConversion = dateTimeForDateConversion;
    }

    public Calendar getDateTimeForCalendarConversion() {
        return dateTimeForCalendarConversion;
    }

    public void setDateTimeForCalendarConversion(Calendar dateTimeForCalendarConversion) {
        this.dateTimeForCalendarConversion = dateTimeForCalendarConversion;
    }
}
