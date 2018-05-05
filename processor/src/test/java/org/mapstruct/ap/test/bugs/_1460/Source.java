/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.bugs._1460;

import java.util.Date;

import org.joda.time.DateTime;

public class Source {
    private String stringToEnum;
    private DateTime jodaDateTimeToCalendar;
    private Date dateToJodaDateTime;

    public String getStringToEnum() {
        return stringToEnum;
    }

    public void setStringToEnum(String stringToEnum) {
        this.stringToEnum = stringToEnum;
    }

    public DateTime getJodaDateTimeToCalendar() {
        return jodaDateTimeToCalendar;
    }

    public void setJodaDateTimeToCalendar(DateTime jodaDateTimeToCalendar) {
        this.jodaDateTimeToCalendar = jodaDateTimeToCalendar;
    }

    public Date getDateToJodaDateTime() {
        return dateToJodaDateTime;
    }

    public void setDateToJodaDateTime(Date dateToJodaDateTime) {
        this.dateToJodaDateTime = dateToJodaDateTime;
    }
}
