/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.locale;

import java.util.Locale;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests conversions between {@link Locale} and String.
 *
 * @author Jason Bodnar
 */
@IssueKey("3172")
@WithClasses({ LocaleSource.class, LocaleTarget.class, LocaleMapper.class })
public class LocaleConversionTest {

    @ProcessorTest
    public void shouldApplyLocaleConversion() {
        LocaleSource source = new LocaleSource();
        source.setLocaleA( Locale.getDefault() );

        LocaleTarget target = LocaleMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getLocaleA() ).isEqualTo( source.getLocaleA().toLanguageTag() );
    }

    @ProcessorTest
    public void shouldApplyReverseLocaleConversion() {
        LocaleTarget target = new LocaleTarget();
        target.setLocaleA( Locale.getDefault().toLanguageTag() );

        LocaleSource source = LocaleMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getLocaleA() ).isEqualTo( Locale.forLanguageTag( target.getLocaleA() ) );
    }
}
