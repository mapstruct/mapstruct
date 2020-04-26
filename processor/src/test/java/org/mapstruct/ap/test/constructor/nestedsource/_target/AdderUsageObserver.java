/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource._target;

/**
 * @author Sjaak Derksen
 */
public class AdderUsageObserver {

    private AdderUsageObserver() {
    }

    private static boolean used = false;

    public static boolean isUsed() {
        return used;
    }

    public static void setUsed(boolean used) {
        AdderUsageObserver.used = used;
    }

}
