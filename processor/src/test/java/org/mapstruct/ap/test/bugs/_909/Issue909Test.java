/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._909;

import org.mapstruct.ap.test.bugs._909.ValuesMapper.ValuesHolder;
import org.mapstruct.ap.test.bugs._909.ValuesMapper.ValuesHolderDto;
import org.mapstruct.factory.Mappers;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that forged iterable mapping methods for multi-dimensional arrays are generated properly.
 *
 * @author Andreas Gudian
 */
@WithClasses(ValuesMapper.class)
public class Issue909Test {
    @ProcessorTest
    public void properlyCreatesMapperWithValuesAsParameterName() {
        ValuesHolder valuesHolder = new ValuesHolder();
        valuesHolder.setValues( "values" );

        ValuesHolderDto dto = Mappers.getMapper( ValuesMapper.class ).convert( valuesHolder );
        assertThat( dto.getValues() ).isEqualTo( "values" );
    }
}
