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
public class Source {

    private FooDto foo;

    public Source( ) {
    }

    public Source( FooDto foo ) {
        this.foo = foo;
    }

    public FooDto getFoo() {
        return foo;
    }

    public void setFoo( FooDto foo ) {
        this.foo = foo;
    }

}
