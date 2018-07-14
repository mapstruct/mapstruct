/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._636;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Issue636Test {

    @Test
    public void shouldMapDataFromJava8Interface() {

        final long idFoo = 123;
        final String idBar = "Bar456";

        final Source source = new Source( idFoo, idBar );

        final Target target = SourceTargetMapper.INSTANCE.mapSourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isNotNull();
        assertThat( target.getFoo().getId() ).isEqualTo( idFoo );
        assertThat( target.getBar() ).isNotNull();
        assertThat( target.getBar().getId() ).isEqualTo( idBar );
    }
}
