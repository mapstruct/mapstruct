/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
