/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3807;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses(Issue3807Mapper.class)
@IssueKey("3807")
class Issue3807Test {

    @ProcessorTest
    void fieldAndSetterShouldWorkWithGeneric() {
        Issue3807Mapper.Source source = new Issue3807Mapper.Source( "value" );
        Issue3807Mapper.TargetWithoutSetter<String> targetWithoutSetter =
            Issue3807Mapper.INSTANCE.mapNoSetter( source );

        assertThat( targetWithoutSetter ).isNotNull();
        assertThat( targetWithoutSetter.value ).isEqualTo( "value" );

        Issue3807Mapper.NormalTarget<String> normalTarget = Issue3807Mapper.INSTANCE.mapNormalSource( source );

        assertThat( normalTarget ).isNotNull();
        assertThat( normalTarget.getValue() ).isEqualTo( "value" );
    }
}
