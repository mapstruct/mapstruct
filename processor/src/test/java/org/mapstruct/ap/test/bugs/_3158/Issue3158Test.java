/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3158;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses(Issue3158Mapper.class)
@IssueKey("3158")
class Issue3158Test {

    @ProcessorTest
    void beanMappingIgnoreByDefaultShouldBeRespectedForConstructorProperties() {
        Issue3158Mapper.Target target = Issue3158Mapper.INSTANCE.map( new Issue3158Mapper.Target(
            "tester",
            "tester@test.com"
        ) );

        assertThat( target.getName() ).isEqualTo( "tester" );
        assertThat( target.getEmail() ).isNull();
    }
}
