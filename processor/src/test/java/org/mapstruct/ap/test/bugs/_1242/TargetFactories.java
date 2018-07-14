/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1242;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

/**
 * Contains non-conflicting factory methods for {@link TargetB}.
 *
 * @author Andreas Gudian
 */
public class TargetFactories {

    @ObjectFactory
    protected TargetB createTargetB(SourceB source, @TargetType Class<TargetB> clazz) {
        return new TargetB( "created by factory" );
    }

    protected TargetB createTargetB(@TargetType Class<TargetB> clazz) {
        throw new RuntimeException( "This method is not to be called" );
    }

    protected TargetB createTargetB() {
        throw new RuntimeException( "This method is not to be called" );
    }
}
