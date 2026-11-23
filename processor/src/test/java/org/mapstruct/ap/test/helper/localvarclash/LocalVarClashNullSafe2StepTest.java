/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.localvarclash;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * Reproduces the {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} bug in the
 * "method to built-in" shape (see {@code MethodBuiltInMapper}).
 * </p>
 * <p>
 * The helper's local variable for the intermediate result is named
 * {@code decapitalize(inputTypeName)} = {@code clashInput}, while the helper's parameter — inherited
 * from the user method {@code toCalendar} — is also named {@code clashInput}. The generated helper
 * is therefore {@code Calendar clashInput = toCalendar( clashInput ); ...}, i.e. two variables with
 * the same name in one scope, which does not compile under either javac or eclipse.
 * </p>
 */
@WithJSpecify
@WithClasses( {
    ClashInput.class,
    ClashSourceBean.class,
    ClashTargetBean.class,
    ClashMapper.class
} )
class LocalVarClashNullSafe2StepTest {

    @ProcessorTest
    void nullSafe2StepLocalVarClash() {
        ClashMapper mapper = ClashMapper.INSTANCE;

        // null input -> null target
        ClashSourceBean nullSource = new ClashSourceBean();
        ClashTargetBean nullTarget = mapper.map( nullSource );
        assertThat( nullTarget.getDateTime() ).isNull();

        // filled input -> the fixed date
        ClashSourceBean filledSource = new ClashSourceBean();
        filledSource.setClashInput( new ClashInput() );
        ClashTargetBean filledTarget = mapper.map( filledSource );
        assertThat( filledTarget.getDateTime() ).isNotNull();
        assertThat( filledTarget.getDateTime() ).isEqualTo(
            new GregorianCalendar( 2020, Calendar.JANUARY, 15 ).toZonedDateTime()
        );
    }
}
