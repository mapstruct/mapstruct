/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._843;

import java.util.Date;

/**
 *
 * @author Sjaak Derksen
 */
public class Commit {

    private static int callCounter;

    private Date authoredDate;

    public Date getAuthoredDate() {
        callCounter++;
        return authoredDate;
    }

    public void setAuthoredDate(Date authoredDate) {
        this.authoredDate = authoredDate;
    }

    public static int getCallCounter() {
        return callCounter;
    }

}
