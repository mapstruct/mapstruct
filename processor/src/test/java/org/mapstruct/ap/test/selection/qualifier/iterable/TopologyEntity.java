/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.iterable;

import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class TopologyEntity {

    private List<TopologyFeatureEntity> topologyFeatures;

    public List<TopologyFeatureEntity> getTopologyFeatures() {
        return topologyFeatures;
    }

    public void setTopologyFeatures(List<TopologyFeatureEntity> topologyFeatures) {
        this.topologyFeatures = topologyFeatures;
    }
}
