/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder._target;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.test.collection.adder.source.Foo;

/**
 *
 * @author Sjaak Derksen
 */
public class Target2 {

    private List<Foo> attributes = new ArrayList<>();

    public Foo addAttribute( Foo foo ) {
        attributes.add( foo );
        return foo;
    }

    public List<Foo> getAttributes() {
        return attributes;
    }

    public void setAttributes( List<Foo> attributes ) {
        this.attributes = attributes;
    }

}
