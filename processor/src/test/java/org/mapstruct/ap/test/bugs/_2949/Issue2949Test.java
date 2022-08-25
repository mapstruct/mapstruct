/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2949;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue2949Mapper.class
})
class Issue2949Test {

    @ProcessorTest
    void shouldCorrectlyInheritInverseBeanMappingWithIgnoreUnmappedSourceProeprties() {
        Issue2949Mapper.Target target = Issue2949Mapper.INSTANCE.toTarget( new Issue2949Mapper.Source(
            "test",
            "first"
        ) );

        assertThat( target.getValue() ).isEqualTo( "test" );

        Issue2949Mapper.Source source = Issue2949Mapper.INSTANCE.toSource( target );

        assertThat( source.getValue() ).isEqualTo( "test" );
        assertThat( source.getProperty1() ).isNull();
    }
}
