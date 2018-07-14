/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import java.util.ArrayList;
import java.util.List;

public class Target {

    private List<TargetFoo> fooListNoSetter;

    public List<TargetFoo> getFooListNoSetter() {
        if ( fooListNoSetter == null ) {
            fooListNoSetter = new ArrayList<TargetFoo>();
        }
        return fooListNoSetter;
    }
}
