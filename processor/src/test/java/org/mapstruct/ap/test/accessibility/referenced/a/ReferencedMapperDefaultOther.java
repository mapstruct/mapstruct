/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced.a;

import org.mapstruct.ap.test.accessibility.referenced.ReferencedSource;
import org.mapstruct.ap.test.accessibility.referenced.ReferencedTarget;

/**
 *
 * @author Sjaak Derksen
 */
public class ReferencedMapperDefaultOther {

     ReferencedTarget sourceToTarget( ReferencedSource source ) {
        ReferencedTarget target = new ReferencedTarget();
        target.setBar( source.getFoo() );
        return target;
    }
}
