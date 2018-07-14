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
public class TimeAndFormat {
    private Date tfTime;
    private String tfFormat;

    public Date getTfTime() {
        return tfTime;
    }

    public void setTfTime(Date tfTime) {
        this.tfTime = tfTime;
    }

    public String getTfFormat() {
        return tfFormat;
    }

    public void setTfFormat(String tfFormat) {
        this.tfFormat = tfFormat;
    }
}
