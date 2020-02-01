/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod;

/**
 * @author Remo Meier
 */
public class Source {

    private Foo prop;

    public Foo getProp() {
        return prop;
    }

    public void setProp(Foo prop) {
        this.prop = prop;
    }
}
