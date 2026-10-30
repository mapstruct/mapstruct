/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3896;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for a custom {@code int -> enum} mapping method being skipped for a nested source property, where the
 * built-in ordinal based conversion was used instead.
 *
 * @author Henrique
 */
@IssueKey("3896")
@WithClasses({
    UserEntity.class,
    GroupEntity.class,
    User.class,
    UserState.class,
    GroupState.class,
    Issue3896Mapper.class
})
class Issue3896Test {

    @ProcessorTest
    void shouldUseCustomMapperForNestedIntToEnum() {
        UserEntity entity = new UserEntity();
        entity.setState( 1 );
        GroupEntity group = new GroupEntity();
        group.setState( 20 );
        entity.setGroup( group );

        User user = Issue3896Mapper.INSTANCE.fromEntity( entity );

        // When the built-in ordinal conversion is used, GroupState.values()[20] would throw
        // (or resolve to the wrong constant); the custom toGroupState must be used instead.
        assertThat( user.getGroupState() ).isEqualTo( GroupState.INACTIVE );
        assertThat( user.getState() ).isEqualTo( UserState.ENABLED );
    }
}
