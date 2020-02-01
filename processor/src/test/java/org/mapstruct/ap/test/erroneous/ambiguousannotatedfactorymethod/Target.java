/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod;

/**
 * @author Remo Meier
 */
public class Target {

    private Bar prop;

    public Bar getProp() {
        return prop;
    }

    public void setProp(Bar prop) {
        this.prop = prop;
    }
}
