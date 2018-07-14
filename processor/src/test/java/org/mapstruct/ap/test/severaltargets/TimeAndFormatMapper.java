/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.severaltargets;

import java.util.Date;

/**
 * @author Sjaak Derksen
 */
public class TimeAndFormatMapper {

    public String getFormat(TimeAndFormat t) {
        return t != null ? t.getTfFormat() : null;
    }

    public Date getTime(TimeAndFormat t) {
        return t != null ? t.getTfTime() : null;
    }
}
