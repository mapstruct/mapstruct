/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion._enum;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WithClasses({
    OptionalEnumToIntegerSource.class,
    EnumToIntegerTarget.class,
    OptionalEnumToIntegerMapper.class,
    EnumToIntegerEnum.class
})
public class OptionalEnumToIntegerConversionTest {

    @ProcessorTest
    public void shouldApplyEnumToIntegerConversion() {
        OptionalEnumToIntegerSource source = new OptionalEnumToIntegerSource();

        for ( EnumToIntegerEnum value : EnumToIntegerEnum.values() ) {
            source.setEnumValue( Optional.of( value ) );

            EnumToIntegerTarget target = OptionalEnumToIntegerMapper.INSTANCE.sourceToTarget( source );

            assertThat( target ).isNotNull();
            assertThat( target.getEnumValue() ).isEqualTo( value.ordinal() );
        }
    }

    @ProcessorTest
    public void shouldApplyReverseEnumToIntegerConversion() {
        EnumToIntegerTarget target = new EnumToIntegerTarget();

        EnumToIntegerEnum[] enumValues = EnumToIntegerEnum.values();
        int numberOfValues = enumValues.length;
        for ( int value = 0; value < numberOfValues; value++ ) {
            target.setEnumValue( value );

            OptionalEnumToIntegerSource source = OptionalEnumToIntegerMapper.INSTANCE.targetToSource( target );

            assertThat( source ).isNotNull();
            assertThat( source.getEnumValue() ).contains( enumValues[target.getEnumValue()] );
        }
    }

    @ProcessorTest
    public void shouldHandleOutOfBoundsEnumOrdinal() {
        EnumToIntegerTarget target = new EnumToIntegerTarget();
        target.setInvalidEnumValue( EnumToIntegerEnum.values().length + 1 );

        assertThatThrownBy( () -> OptionalEnumToIntegerMapper.INSTANCE.targetToSource( target ) )
            .isInstanceOf( ArrayIndexOutOfBoundsException.class );
    }

    @ProcessorTest
    public void shouldHandleNullIntegerValue() {
        EnumToIntegerTarget target = new EnumToIntegerTarget();

        OptionalEnumToIntegerSource source = OptionalEnumToIntegerMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getEnumValue() ).isEmpty();
        assertThat( source.getInvalidEnumValue() ).isEmpty();
    }
}
