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
package org.mapstruct.ap.test.bugs._1523.java8;

import java.time.ZonedDateTime;
import java.util.Calendar;

public class Source {
    private ZonedDateTime value;
    private Calendar value2;

    public ZonedDateTime getValue() {
        return value;
    }

    public void setValue(ZonedDateTime value) {
        this.value = value;
    }

    public Calendar getValue2() {
        return value2;
    }

    public void setValue2(Calendar value2) {
        this.value2 = value2;
    }
}
