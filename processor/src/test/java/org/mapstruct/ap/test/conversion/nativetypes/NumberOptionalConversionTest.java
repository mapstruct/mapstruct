/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.nativetypes;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    ByteTarget.class,
    ByteWrapperOptionalSource.class,
    ByteWrapperTarget.class,
    ShortTarget.class,
    ShortWrapperOptionalSource.class,
    ShortWrapperTarget.class,
    IntOptionalSource.class,
    IntTarget.class,
    IntWrapperOptionalSource.class,
    IntWrapperTarget.class,
    LongOptionalSource.class,
    LongTarget.class,
    LongWrapperOptionalSource.class,
    LongWrapperTarget.class,
    FloatWrapperOptionalSource.class,
    FloatWrapperTarget.class,
    DoubleOptionalSource.class,
    DoubleTarget.class,
    DoubleWrapperOptionalSource.class,
    DoubleWrapperTarget.class,
    OptionalNumberConversionMapper.class
})
public class NumberOptionalConversionTest {

    @ProcessorTest
    public void shouldApplyByteWrapperConversions() {
        ByteWrapperOptionalSource source = new ByteWrapperOptionalSource();
        source.setB( Optional.of( (byte) 1 ) );
        source.setBb( Optional.of( (byte) 2 ) );
        source.setS( Optional.of( (byte) 3 ) );
        source.setSs( Optional.of( (byte) 4 ) );
        source.setI( Optional.of( (byte) 5 ) );
        source.setIi( Optional.of( (byte) 6 ) );
        source.setL( Optional.of( (byte) 7 ) );
        source.setLl( Optional.of( (byte) 8 ) );
        source.setF( Optional.of( (byte) 9 ) );
        source.setFf( Optional.of( (byte) 10 ) );
        source.setD( Optional.of( (byte) 11 ) );
        source.setDd( Optional.of( (byte) 12 ) );

        ByteWrapperTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    public void shouldApplyShortWrapperConversions() {
        ShortWrapperOptionalSource source = new ShortWrapperOptionalSource();
        source.setB( Optional.of( (short) 1 ) );
        source.setBb( Optional.of( (short) 2 ) );
        source.setS( Optional.of( (short) 3 ) );
        source.setSs( Optional.of( (short) 4 ) );
        source.setI( Optional.of( (short) 5 ) );
        source.setIi( Optional.of( (short) 6 ) );
        source.setL( Optional.of( (short) 7 ) );
        source.setLl( Optional.of( (short) 8 ) );
        source.setF( Optional.of( (short) 9 ) );
        source.setFf( Optional.of( (short) 10 ) );
        source.setD( Optional.of( (short) 11 ) );
        source.setDd( Optional.of( (short) 12 ) );

        ShortWrapperTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    public void shouldApplyIntConversions() {
        IntOptionalSource source = new IntOptionalSource();
        source.setB( OptionalInt.of( 1 ) );
        source.setBb( OptionalInt.of( 2 ) );
        source.setS( OptionalInt.of( 3 ) );
        source.setSs( OptionalInt.of( 4 ) );
        source.setI( OptionalInt.of( 5 ) );
        source.setIi( OptionalInt.of( 6 ) );
        source.setL( OptionalInt.of( 7 ) );
        source.setLl( OptionalInt.of( 8 ) );
        source.setF( OptionalInt.of( 9 ) );
        source.setFf( OptionalInt.of( 10 ) );
        source.setD( OptionalInt.of( 11 ) );
        source.setDd( OptionalInt.of( 12 ) );

        IntTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    public void shouldApplyIntWrapperConversions() {
        IntWrapperOptionalSource source = new IntWrapperOptionalSource();
        source.setB( Optional.of( 1 ) );
        source.setBb( Optional.of( 2 ) );
        source.setS( Optional.of( 3 ) );
        source.setSs( Optional.of( 4 ) );
        source.setI( Optional.of( 5 ) );
        source.setIi( Optional.of( 6 ) );
        source.setL( Optional.of( 7 ) );
        source.setLl( Optional.of( 8 ) );
        source.setF( Optional.of( 9 ) );
        source.setFf( Optional.of( 10 ) );
        source.setD( Optional.of( 11 ) );
        source.setDd( Optional.of( 12 ) );

        IntWrapperTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    public void shouldApplyLongConversions() {
        LongOptionalSource source = new LongOptionalSource();
        source.setB( OptionalLong.of( 1 ) );
        source.setBb( OptionalLong.of( 2 ) );
        source.setS( OptionalLong.of( 3 ) );
        source.setSs( OptionalLong.of( 4 ) );
        source.setI( OptionalLong.of( 5 ) );
        source.setIi( OptionalLong.of( 6 ) );
        source.setL( OptionalLong.of( 7 ) );
        source.setLl( OptionalLong.of( 8 ) );
        source.setF( OptionalLong.of( 9 ) );
        source.setFf( OptionalLong.of( 10 ) );
        source.setD( OptionalLong.of( 11 ) );
        source.setDd( OptionalLong.of( 12 ) );

        LongTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    public void shouldApplyLongWrapperConversions() {
        LongWrapperOptionalSource source = new LongWrapperOptionalSource();
        source.setB( Optional.of( (long) 1 ) );
        source.setBb( Optional.of( (long) 2 ) );
        source.setS( Optional.of( (long) 3 ) );
        source.setSs( Optional.of( (long) 4 ) );
        source.setI( Optional.of( (long) 5 ) );
        source.setIi( Optional.of( (long) 6 ) );
        source.setL( Optional.of( (long) 7 ) );
        source.setLl( Optional.of( (long) 8 ) );
        source.setF( Optional.of( (long) 9 ) );
        source.setFf( Optional.of( (long) 10 ) );
        source.setD( Optional.of( (long) 11 ) );
        source.setDd( Optional.of( (long) 12 ) );

        LongWrapperTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    public void shouldApplyFloatWrapperConversions() {
        FloatWrapperOptionalSource source = new FloatWrapperOptionalSource();
        source.setB( Optional.of( 1f ) );
        source.setBb( Optional.of( 2f ) );
        source.setS( Optional.of( 3f ) );
        source.setSs( Optional.of( 4f ) );
        source.setI( Optional.of( 5f ) );
        source.setIi( Optional.of( 6f ) );
        source.setL( Optional.of( 7f ) );
        source.setLl( Optional.of( 8f ) );
        source.setF( Optional.of( 9f ) );
        source.setFf( Optional.of( 10f ) );
        source.setD( Optional.of( 11f ) );
        source.setDd( Optional.of( 12f ) );

        FloatWrapperTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    public void shouldApplyDoubleConversions() {
        DoubleOptionalSource source = new DoubleOptionalSource();
        source.setB( OptionalDouble.of( 1 ) );
        source.setBb( OptionalDouble.of( 2 ) );
        source.setS( OptionalDouble.of( 3 ) );
        source.setSs( OptionalDouble.of( 4 ) );
        source.setI( OptionalDouble.of( 5 ) );
        source.setIi( OptionalDouble.of( 6 ) );
        source.setL( OptionalDouble.of( 7 ) );
        source.setLl( OptionalDouble.of( 8 ) );
        source.setF( OptionalDouble.of( 9 ) );
        source.setFf( OptionalDouble.of( 10 ) );
        source.setD( OptionalDouble.of( 11 ) );
        source.setDd( OptionalDouble.of( 12 ) );

        DoubleTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    public void shouldApplyDoubleWrapperConversions() {
        DoubleWrapperOptionalSource source = new DoubleWrapperOptionalSource();
        source.setB( Optional.of( 1d ) );
        source.setBb( Optional.of( 2d ) );
        source.setS( Optional.of( 3d ) );
        source.setSs( Optional.of( 4d ) );
        source.setI( Optional.of( 5d ) );
        source.setIi( Optional.of( 6d ) );
        source.setL( Optional.of( 7d ) );
        source.setLl( Optional.of( 8d ) );
        source.setF( Optional.of( 9d ) );
        source.setFf( Optional.of( 10d ) );
        source.setD( Optional.of( 11d ) );
        source.setDd( Optional.of( 12d ) );

        DoubleWrapperTarget target = OptionalNumberConversionMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( (byte) 1 );
        assertThat( target.getBb() ).isEqualTo( Byte.valueOf( (byte) 2 ) );
        assertThat( target.getS() ).isEqualTo( (short) 3 );
        assertThat( target.getSs() ).isEqualTo( Short.valueOf( (short) 4 ) );
        assertThat( target.getI() ).isEqualTo( 5 );
        assertThat( target.getIi() ).isEqualTo( Integer.valueOf( 6 ) );
        assertThat( target.getL() ).isEqualTo( 7 );
        assertThat( target.getLl() ).isEqualTo( Long.valueOf( 8 ) );
        assertThat( target.getF() ).isEqualTo( 9f );
        assertThat( target.getFf() ).isEqualTo( Float.valueOf( 10f ) );
        assertThat( target.getD() ).isEqualTo( 11d );
        assertThat( target.getDd() ).isEqualTo( Double.valueOf( 12d ) );
    }

    @ProcessorTest
    @IssueKey("229")
    public void wrapperToPrimitiveIsNullSafe() {
        assertThat( OptionalNumberConversionMapper.INSTANCE.sourceToTarget( new ByteWrapperOptionalSource() ) )
            .isNotNull();
        assertThat( OptionalNumberConversionMapper.INSTANCE.sourceToTarget( new DoubleWrapperOptionalSource() ) )
            .isNotNull();
        assertThat( OptionalNumberConversionMapper.INSTANCE.sourceToTarget( new ShortWrapperOptionalSource() ) )
            .isNotNull();
        assertThat( OptionalNumberConversionMapper.INSTANCE.sourceToTarget( new IntWrapperOptionalSource() ) )
            .isNotNull();
        assertThat( OptionalNumberConversionMapper.INSTANCE.sourceToTarget( new FloatWrapperOptionalSource() ) )
            .isNotNull();
        assertThat( OptionalNumberConversionMapper.INSTANCE.sourceToTarget( new LongWrapperOptionalSource() ) )
            .isNotNull();
    }
}
