/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-03-13T22:39:43+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
public class AbstractSourceTargetMapperPrivateImpl extends AbstractSourceTargetMapperPrivate {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setReferencedTarget( referencedSourceToReferencedTarget( source.getReferencedSource() ) );

        return target;
    }

    protected ReferencedTarget referencedSourceToReferencedTarget(ReferencedSource referencedSource) {
        if ( referencedSource == null ) {
            return null;
        }

        ReferencedTarget referencedTarget = new ReferencedTarget();

        return referencedTarget;
    }
}
