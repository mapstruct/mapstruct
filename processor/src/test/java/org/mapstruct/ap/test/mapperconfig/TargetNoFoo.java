/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mapperconfig;

/**
 *
 * @author Sjaak Derksen
 */
public class TargetNoFoo {

    private FooEntity noFoo;

    public FooEntity getNoFoo() {
        return noFoo;
    }

    public void setNoFoo( FooEntity noFoo ) {
        this.noFoo = noFoo;
    }

}
