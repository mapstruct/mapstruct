/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2538;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Group.class,
    GroupDto.class,
    TeamMapper.class,
    TeamRole.class,
    TeamRoleDto.class,
})
class Issue2538Test {

    @ProcessorTest
    void shouldCorrectlyUseQualifiedMethodIn2StepMapping() {
        TeamRole role = TeamMapper.INSTANCE.mapUsingFirstLookup( new TeamRoleDto( "test" ) );

        assertThat( role ).isNotNull();
        assertThat( role.getGroup() ).isNotNull();
        assertThat( role.getGroup().getId() ).isEqualTo( "lookup-test" );

        role = TeamMapper.INSTANCE.mapUsingSecondLookup( new TeamRoleDto( "test" ) );

        assertThat( role ).isNotNull();
        assertThat( role.getGroup() ).isNotNull();
        assertThat( role.getGroup().getId() ).isEqualTo( "second-test" );
    }
}
