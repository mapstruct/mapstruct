/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2122;

import java.util.List;
import java.util.Map;

public class Target {
    List<EmbeddedTarget> embeddedTarget;

    Map<String, EmbeddedTarget> embeddedMapTarget;

    public List<EmbeddedTarget> getEmbeddedTarget() {
        return embeddedTarget;
    }

    public void setEmbeddedTarget(List<EmbeddedTarget> embeddedTarget) {
        this.embeddedTarget = embeddedTarget;
    }

    public Map<String, EmbeddedTarget> getEmbeddedMapTarget() {
        return embeddedMapTarget;
    }

    public void setEmbeddedMapTarget( Map<String, EmbeddedTarget> embeddedMapTarget) {
        this.embeddedMapTarget = embeddedMapTarget;
    }
}
