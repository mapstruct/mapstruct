/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced;

/**
 *
 * @author Sjaak Derksen
 */
public class Target {

    private ReferencedTarget referencedTarget;

    public ReferencedTarget getReferencedTarget() {
        return referencedTarget;
    }

    public void setReferencedTarget( ReferencedTarget referencedTarget ) {
        this.referencedTarget = referencedTarget;
    }

}
