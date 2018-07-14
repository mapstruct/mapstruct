/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bool;

/**
 * @author Andreas Gudian
 *
 */
public class YesNoMapper {
    public String toString(YesNo yesNo) {
        if ( null != yesNo && yesNo.isYes() ) {
            return "yes";
        }

        return "no";
    }

    public boolean toBool(YesNo yesNo) {
        return ( null != yesNo && yesNo.isYes() );
    }
}
