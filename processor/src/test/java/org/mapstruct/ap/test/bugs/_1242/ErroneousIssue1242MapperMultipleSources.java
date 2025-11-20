/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1242;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

/**
 * Results in an ambiguous factory method error, as there are two methods with matching source types available.
 *
 * @author Andreas Gudian
 */
@Mapper(uses = TargetFactories.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ErroneousIssue1242MapperMultipleSources {
    abstract TargetA toTargetA(SourceA source);

    abstract TargetB toTargetB(SourceB source);

    @ObjectFactory
    protected TargetB anotherTargetBCreator(SourceB source) {
        throw new RuntimeException( "never to be called" );
    }
}
