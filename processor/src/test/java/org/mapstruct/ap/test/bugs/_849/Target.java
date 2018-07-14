/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._849;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class Target {

    private List<Serializable> targetList;

    public List<Serializable> getTargetList() {
        if ( targetList == null ) {
            targetList = new ArrayList<Serializable>();
        }
        return targetList;
    }

}
