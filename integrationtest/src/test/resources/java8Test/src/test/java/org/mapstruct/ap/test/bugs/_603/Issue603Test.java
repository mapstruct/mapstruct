/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._603;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

public class Issue603Test {

    @Test
    public void shouldMapDataFromJava8Interface() {

        final Source source = new Source();

        final Target target = SourceTargetMapper.INSTANCE.mapSourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValue1() ).isEqualTo( "foo" );
        assertThat( target.getValue2() ).isEqualTo( "bar" );
    }
}
