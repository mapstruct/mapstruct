/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3708;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.bugs._3708.dto.PartnerDto;
import org.mapstruct.ap.test.bugs._3708.entity.Partner;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@IssueKey("3708")
@WithClasses({
    Partner.class,
    PartnerDto.class,
    Issue3708TargetImmutableMapper.class,
    Issue3708AccessorOnlyMapper.class
})
public class Issue3708Test {

    @RegisterExtension
    final GeneratedSource generated = new GeneratedSource();

    @ProcessorTest
    void beanMappingAccessorOnlyShouldThrowWhenUpdatesImmutableTarget() {
        Partner entity = new Partner();
        entity.setTypes( Set.of( "A" ) );
        PartnerDto dto = new PartnerDto();
        dto.setTypes( new LinkedHashSet<>( Set.of( "B", "C" ) ) );

        assertThatThrownBy( () -> Issue3708AccessorOnlyMapper.INSTANCE.partialUpdateBeanMapping( entity, dto ) )
            .isInstanceOf( UnsupportedOperationException.class );
    }

    @ProcessorTest
    void beanMappingTargetImmutableShouldReplaceWhenUpdatesImmutableTarget() {
        Partner entity = new Partner();
        entity.setTypes( Set.of( "A" ) );
        PartnerDto dto = new PartnerDto();
        dto.setTypes( new LinkedHashSet<>( Set.of( "B", "C" ) ) );

        Issue3708TargetImmutableMapper.INSTANCE.partialUpdateBeanMapping( entity, dto );

        assertThat( entity.getTypes() ).containsExactlyInAnyOrder( "B", "C" );
    }

    @ProcessorTest
    void mappingTargetImmutableShouldReplaceWhenUpdatesImmutableTarget() {
        Partner entity = new Partner();
        entity.setTypes( Set.of( "A" ) );
        PartnerDto dto = new PartnerDto();
        dto.setTypes( new LinkedHashSet<>( Set.of( "B", "C" ) ) );

        Issue3708TargetImmutableMapper.INSTANCE.partialUpdateMapping( entity, dto );

        assertThat( entity.getTypes() ).containsExactlyInAnyOrder( "B", "C" );
    }

    @ProcessorTest
    void clearAndAddAllShouldNotGenerateWhenTargetImmutable() {
        generated.forMapper( Issue3708TargetImmutableMapper.class )
            .content()
            .doesNotContain( ".clear(", ".addAll(" );
    }

    @ProcessorTest
    void clearAndAddAllShouldGenerateWhenAccessorOnly() {
        generated.forMapper( Issue3708AccessorOnlyMapper.class )
            .content()
            .contains( ".clear(", ".addAll(" );
    }
}
