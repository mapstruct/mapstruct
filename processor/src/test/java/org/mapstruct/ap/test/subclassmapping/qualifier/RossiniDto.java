/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.qualifier;

import java.util.List;

public class RossiniDto extends ComposerDto {
    private List<String> crescendo;

    public List<String> getCrescendo() {
        return crescendo;
    }

    public void setCrescendo(List<String> crescendo) {
        this.crescendo = crescendo;
    }
}
