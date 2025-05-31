/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3711;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    ParentEntity.class,
    ParentDto.class,
    JpaContext.class,
    SourceTargetMapper.class,
    BaseMapper.class,
})
@IssueKey("3711")
class Issue3711Test {
    @ProcessorTest
    void shouldGenerateContextMethod() {
        JpaContext<ParentEntity> jpaContext = new JpaContext<>();
        SourceTargetMapper.INSTANCE.toEntity( new ParentDto(), jpaContext );

        assertThat( jpaContext.getInvokedMethods() )
            .containsExactly( "beforeMapping", "afterMapping" );
    }
}
