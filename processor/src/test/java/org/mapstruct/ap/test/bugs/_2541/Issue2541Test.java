/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2541;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue2541Mapper.class,
    Nullable.class,
})
class Issue2541Test {

    @ProcessorTest
    void shouldGenerateCorrectCode() {
        Issue2541Mapper.Target target = Issue2541Mapper.INSTANCE.map( new Issue2541Mapper.Source( null ) );

        assertThat( target.getValue() ).isEmpty();
    }
}
