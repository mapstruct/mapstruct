/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._513;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Sjaak Derksen
 */
public class Target {

    private  Collection<TargetElement> collection;
    private Map<TargetKey, TargetValue> map;

    public Collection<TargetElement> getCollection() {
        return collection;
    }

    public void setCollection( Collection<TargetElement>  collection ) {
        this.collection = collection;
    }

    public Map<TargetKey, TargetValue> getMap() {
        return map;
    }

    public void setMap(Map<TargetKey, TargetValue> map) {
        this.map = map;
    }

}
