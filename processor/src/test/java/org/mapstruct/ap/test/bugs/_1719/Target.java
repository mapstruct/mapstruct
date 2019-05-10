/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1719;

import java.util.HashSet;
import java.util.Set;

public class Target {

    private Set<TargetElement> targetElements = new HashSet<>();

    public Set<TargetElement> getTargetElements() {
        return targetElements;
    }

    public void setTargetElements(Set<TargetElement> targetElements) {
        this.targetElements = targetElements;
    }

    public TargetElement addTargetElement(TargetElement element) {
        element.updateTarget( this );
        getTargetElements().add( element );
        return element;
    }

    public TargetElement removeTargetElement(TargetElement element) {
        element.updateTarget( null );
        getTargetElements().remove( element );
        return element;
    }

}
