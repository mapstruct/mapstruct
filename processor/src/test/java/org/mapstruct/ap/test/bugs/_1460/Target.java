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

import java.util.Calendar;

import org.joda.time.DateTime;

public class Target {
    public enum Issue1460Enum {
        OK, KO;
    }

    private Issue1460Enum stringToEnum;
    private Calendar jodaDateTimeToCalendar;
    private DateTime dateToJodaDateTime;

    public Issue1460Enum getStringToEnum() {
        return stringToEnum;
    }

    public void setStringToEnum(Issue1460Enum stringToEnum) {
        this.stringToEnum = stringToEnum;
    }

    public Calendar getJodaDateTimeToCalendar() {
        return jodaDateTimeToCalendar;
    }

    public void setJodaDateTimeToCalendar(Calendar jodaDateTimeToCalendar) {
        this.jodaDateTimeToCalendar = jodaDateTimeToCalendar;
    }

    public DateTime getDateToJodaDateTime() {
        return dateToJodaDateTime;
    }

    public void setDateToJodaDateTime(DateTime dateToJodaDateTime) {
        this.dateToJodaDateTime = dateToJodaDateTime;
    }
}
