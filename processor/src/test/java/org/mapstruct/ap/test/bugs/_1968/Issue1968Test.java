/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1968;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1968")
@WithClasses({
    Animal.class,
    AnimalDto.class
})
class Issue1968Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses(Issue1968Mapper.class)
    void shouldReuseForgedIterableMethodForExplicitAndImplicitPropertyMapping() {
        generatedSource.addComparisonToFixtureFor( Issue1968Mapper.class );

        AnimalDto dto = new AnimalDto();
        dto.setNames( new String[] { "Rex" } );
        dto.setOtherNames( new String[] { "Doggo", "Good boy" } );

        Animal animal = Issue1968Mapper.INSTANCE.dtoToEntity( dto );

        assertThat( animal.getNames() ).containsExactly( "Rex" );
        assertThat( animal.getSpecialNames() ).containsExactly( "Doggo", "Good boy" );
    }

    @ProcessorTest
    @WithClasses(Issue1968ConditionMapper.class)
    void shouldReuseForgedIterableMethodWhenPropertyMappingHasConditionQualifier() {
        generatedSource.addComparisonToFixtureFor( Issue1968ConditionMapper.class );

        AnimalDto dto = new AnimalDto();
        dto.setNames( new String[] { "Rex" } );
        dto.setOtherNames( new String[0] );

        Animal animal = Issue1968ConditionMapper.INSTANCE.dtoToEntity( dto );

        assertThat( animal.getNames() ).containsExactly( "Rex" );
        // the condition is still applied to the property mapping itself
        assertThat( animal.getSpecialNames() ).isNull();

        dto.setOtherNames( new String[] { "Doggo" } );

        animal = Issue1968ConditionMapper.INSTANCE.dtoToEntity( dto );

        assertThat( animal.getSpecialNames() ).containsExactly( "Doggo" );
    }
}
