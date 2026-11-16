/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion._enum;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests conversions between {@link Enum} and Integer.
 *
 * @author Jose Carlos Campanero Ortiz
 */
@IssueKey("2963")
@WithClasses({
    EnumToIntegerSource.class,
    EnumToIntegerTarget.class,
    EnumToIntegerMapper.class,
    EnumToIntegerEnum.class
})
public class EnumToIntegerConversionTest {

    @ProcessorTest
    public void shouldApplyEnumToIntegerConversion() {
        EnumToIntegerSource source = new EnumToIntegerSource();

        for ( EnumToIntegerEnum value : EnumToIntegerEnum.values() ) {
            source.setEnumValue( value );

            EnumToIntegerTarget target = EnumToIntegerMapper.INSTANCE.sourceToTarget( source );

            assertThat( target ).isNotNull();
            assertThat( target.getEnumValue() ).isEqualTo( source.getEnumValue().ordinal() );
        }
    }

    @ProcessorTest
    public void shouldApplyReverseEnumToIntegerConversion() {
        EnumToIntegerTarget target = new EnumToIntegerTarget();

        int numberOfValues = EnumToIntegerEnum.values().length;
        for ( int value = 0; value < numberOfValues; value++ ) {
            target.setEnumValue( value );

            EnumToIntegerSource source = EnumToIntegerMapper.INSTANCE.targetToSource( target );

            assertThat( source ).isNotNull();
            assertThat( source.getEnumValue() ).isEqualTo( EnumToIntegerEnum.values()[ target.getEnumValue() ] );
        }
    }

    @ProcessorTest
    public void shouldHandleOutOfBoundsEnumOrdinal() {
        EnumToIntegerTarget target = new EnumToIntegerTarget();
        target.setInvalidEnumValue( EnumToIntegerEnum.values().length + 1 );

        assertThatThrownBy( () -> EnumToIntegerMapper.INSTANCE.targetToSource( target ) )
                .isInstanceOf( ArrayIndexOutOfBoundsException.class );
    }

    @ProcessorTest
    public void shouldHandleNullIntegerValue() {
        EnumToIntegerTarget target = new EnumToIntegerTarget();

        EnumToIntegerSource source = EnumToIntegerMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getEnumValue() ).isNull();
        assertThat( source.getInvalidEnumValue() ).isNull();
    }
}
