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
public class TopologyDto {

    private List<TopologyFeatureDto> topologyFeatures;

    public List<TopologyFeatureDto> getTopologyFeatures() {
        return topologyFeatures;
    }

    public void setTopologyFeatures(List<TopologyFeatureDto> topologyFeatures) {
        this.topologyFeatures = topologyFeatures;
    }

}
