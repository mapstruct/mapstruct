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
package org.mapstruct.ap.test.conversion.erroneous;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private String date;

    private String zonedDateTime;
    private String localDateTime;
    private String localDate;
    private String localTime;

    private String dateTime;
    private String jodaLocalDateTime;
    private String jodaLocalDate;
    private String jodaLocalTime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(String zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getJodaLocalDateTime() {
        return jodaLocalDateTime;
    }

    public void setJodaLocalDateTime(String jodaLocalDateTime) {
        this.jodaLocalDateTime = jodaLocalDateTime;
    }

    public String getJodaLocalDate() {
        return jodaLocalDate;
    }

    public void setJodaLocalDate(String jodaLocalDate) {
        this.jodaLocalDate = jodaLocalDate;
    }

    public String getJodaLocalTime() {
        return jodaLocalTime;
    }

    public void setJodaLocalTime(String jodaLocalTime) {
        this.jodaLocalTime = jodaLocalTime;
    }
}
