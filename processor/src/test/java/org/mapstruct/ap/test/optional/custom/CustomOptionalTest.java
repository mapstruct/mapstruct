/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.custom;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses(CustomOptionalMapper.class)
class CustomOptionalTest {

    @ProcessorTest
    void shouldUseCustomMethodsWhenMapping() {
        CustomOptionalMapper.Target target = CustomOptionalMapper.INSTANCE.map( new CustomOptionalMapper.Source( null ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "initial" );

        target = CustomOptionalMapper.INSTANCE.map( new CustomOptionalMapper.Source( Optional.empty() ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();

        target = CustomOptionalMapper.INSTANCE.map( new CustomOptionalMapper.Source( Optional.of( "test" ) ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "test" );
    }

    @ProcessorTest
    void shouldUseCustomMethodsWhenUpdating() {
        CustomOptionalMapper.Target target = new CustomOptionalMapper.Target();

        CustomOptionalMapper.INSTANCE.update( target, new CustomOptionalMapper.Source( null ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "initial" );

        CustomOptionalMapper.INSTANCE.update( target, new CustomOptionalMapper.Source( Optional.empty() ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();

        target = new CustomOptionalMapper.Target();
        CustomOptionalMapper.INSTANCE.update( target, new CustomOptionalMapper.Source( Optional.of( "test" ) ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "test" );
    }
}
