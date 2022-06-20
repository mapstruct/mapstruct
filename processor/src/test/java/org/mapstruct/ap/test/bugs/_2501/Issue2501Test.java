/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2501;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue2501Mapper.class
})
class Issue2501Test {

    @ProcessorTest
    void shouldUnwrapEnumOptional() {
        Issue2501Mapper.CustomerDTO source = new Issue2501Mapper.CustomerDTO();
        source.setStatus( Issue2501Mapper.DtoStatus.DISABLED );

        Issue2501Mapper.Customer target = Issue2501Mapper.INSTANCE.map( source );

        assertThat( target.getStatus() ).isEqualTo( Issue2501Mapper.CustomerStatus.DISABLED );
    }
}
