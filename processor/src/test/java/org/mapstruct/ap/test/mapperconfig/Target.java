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
public class Target {

    private FooEntity foo;

    public Target() {
    }

    public Target( FooEntity foo ) {
        this.foo = foo;
    }

    public FooEntity getFoo() {
        return foo;
    }

    public void setFoo( FooEntity foo ) {
        this.foo = foo;
    }

}
