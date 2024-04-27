/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

import java.util.List;

public class TargetContainer {
    private Target target;
    List<TargetCollectionItem> targetCollection;

    public Target getTarget() {
        return target;
    }

    public List<TargetCollectionItem> getTargetCollection() {
        return targetCollection;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setTargetCollection(List<TargetCollectionItem> targetCollection) {
        this.targetCollection = targetCollection;
    }
}