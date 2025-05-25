/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3611;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3611")
@WithClasses({
    Issue3611Mapper.class
})
public class Issue3611Test {

    @ProcessorTest
    public void shouldInverseConvert() {
        Issue3611Mapper.Target target = new Issue3611Mapper.Target( "type", "baseAttribute", "sourceAttribute" );

        Issue3611Mapper.Source source = Issue3611Mapper.INSTANCE.inverseConvert( target );

        assertThat( source ).isNotNull();
        assertThat( source.getType() ).isNull();
        assertThat( source.getBaseAttribute() ).isEqualTo( "baseAttribute" );
        assertThat( source.getSourceAttribute() ).isEqualTo( "sourceAttribute" );
    }
}
