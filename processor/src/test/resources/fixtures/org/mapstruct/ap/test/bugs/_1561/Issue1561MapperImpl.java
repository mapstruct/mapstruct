/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1561;

import java.util.stream.Stream;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-01-29T19:40:52+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class Issue1561MapperImpl implements Issue1561Mapper {

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        target.setNestedTarget( sourceToNestedTarget( source ) );

        return target;
    }

    @Override
    public Source map(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        Stream<String> nestedTargetProperty = targetNestedTargetProperties( target );
        if ( nestedTargetProperty != null ) {
            nestedTargetProperty.forEach( source::addProperty );
        }

        return source;
    }

    protected NestedTarget sourceToNestedTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        NestedTarget nestedTarget = new NestedTarget();

        if ( source.getProperties() != null ) {
            for ( String property : source.getProperties() ) {
                nestedTarget.addProperty( property );
            }
        }

        return nestedTarget;
    }

    private Stream<String> targetNestedTargetProperties(Target target) {
        NestedTarget nestedTarget = target.getNestedTarget();
        if ( nestedTarget == null ) {
            return null;
        }
        return nestedTarget.getProperties();
    }
}
