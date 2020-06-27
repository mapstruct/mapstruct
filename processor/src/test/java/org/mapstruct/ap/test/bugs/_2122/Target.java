/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2122;

import java.util.List;

public class Target {
    List<EmbeddedTarget> embeddedTarget;

    public List<EmbeddedTarget> getEmbeddedTarget() {
        return embeddedTarget;
    }

    public void setEmbeddedTarget(List<EmbeddedTarget> embeddedTarget) {
        this.embeddedTarget = embeddedTarget;
    }
}
