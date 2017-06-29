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
package org.mapstruct.ap.test.nestedbeans.exclusions;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Filip Hrisafov
 */
class Target {

    class TargetDeepNested {
        //CHECKSTYLE:OFF
        public List<String> types;
        //CHECKSTYLE:ON
    }

    class TargetNested {
        //CHECKSTYLE:OFF
        public TargetDeepNested deepNestedType;
        //CHECKSTYLE:ON
    }

    //CHECKSTYLE:OFF
    public Date date;
    public GregorianCalendar calendar;
    public List<String> types;
    public TargetNested nestedMyType;
    //CHECKSTYLE:ON
}
