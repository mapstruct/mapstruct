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
public class SourceTargetMapperProtectedBase {

    protected ReferencedTarget sourceToTarget( ReferencedSource source ) {
        ReferencedTarget target = new ReferencedTarget();
        target.setBar( source.getFoo() );
        return target;
    }
}
