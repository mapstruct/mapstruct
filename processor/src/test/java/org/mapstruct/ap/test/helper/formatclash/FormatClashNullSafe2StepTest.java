/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.formatclash;

import java.time.LocalDate;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    FormatClashSource.class,
    FormatClashTarget.class
})
class FormatClashNullSafe2StepTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses(FormatClashMapper.class)
    void sameMethodWithDifferentFormatsUsesOwnHelper() {
        FormatClashSource source = new FormatClashSource();
        source.setValue( new FormatClashSource.Value( 1234, LocalDate.of( 2026, 11, 21 ) ) );

        FormatClashTarget target = FormatClashMapper.INSTANCE.map( source );

        assertThat( target.getDecimal() ).isEqualTo( "1234.00" );
        assertThat( target.getGrouped() ).isEqualTo( "1,234" );
        assertThat( target.getSameFormat() ).isEqualTo( "1234.00" );
        assertThat( target.getIsoDate() ).isEqualTo( "2026-11-21" );
        assertThat( target.getGermanDate() ).isEqualTo( "21.11.2026" );
    }

    @ProcessorTest
    @WithClasses(FormatClashMapper.class)
    void sameMethodWithSameFormatReusesHelper() {
        generatedSource.forMapper( FormatClashMapper.class )
            .content()
            .containsOnlyOnce( "new DecimalFormat( \"#.00\" )" );
    }

    @ProcessorTest
    @WithClasses(FormatClashMapper.class)
    void nullValueIsMappedToNull() {
        FormatClashSource source = new FormatClashSource();
        source.setValue( new FormatClashSource.Value( null, null ) );

        FormatClashTarget target = FormatClashMapper.INSTANCE.map( source );

        assertThat( target.getDecimal() ).isNull();
        assertThat( target.getGrouped() ).isNull();
        assertThat( target.getIsoDate() ).isNull();
        assertThat( target.getGermanDate() ).isNull();
    }

    @ProcessorTest
    @WithClasses(ExistingMethodNameClashMapper.class)
    void helperDoesNotClashWithExistingMethodName() {
        FormatClashSource source = new FormatClashSource();
        source.setValue( new FormatClashSource.Value( 1234, null ) );

        FormatClashTarget target = ExistingMethodNameClashMapper.INSTANCE.map( source );

        assertThat( target.getDecimal() ).isEqualTo( "1234.00" );
    }
}
