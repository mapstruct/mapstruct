/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JavaTimeConstants;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

import static org.mapstruct.ap.internal.conversion.ReverseConversion.reverse;

/**
 * Holds built-in {@link ConversionProvider}s such as from {@code int} to {@code String}.
 *
 * @author Gunnar Morling
 */
public class Conversions {

    private final Map<Key, ConversionProvider> conversions = new HashMap<>();
    private final Type enumType;
    private final Type stringType;
    private final TypeFactory typeFactory;

    public Conversions(Elements elementUtils, TypeFactory typeFactory) {
        this.typeFactory = typeFactory;

        this.enumType = typeFactory.getType( Enum.class );
        this.stringType = typeFactory.getType( String.class );

        //native (number) types <> native types, including wrappers
        registerNativeNumberConversions();

        registerWideningOrSameConversion( boolean.class, Boolean.class, false );
        registerWideningOrSameConversion( Boolean.class, boolean.class, false );

        //BigInteger <> native types
        registerBigIntegerConversion( byte.class );
        registerBigIntegerConversion( Byte.class );
        registerBigIntegerConversion( short.class );
        registerBigIntegerConversion( Short.class );
        registerBigIntegerConversion( int.class );
        registerBigIntegerConversion( Integer.class );
        registerBigIntegerConversion( long.class );
        registerBigIntegerConversion( Long.class );
        registerBigIntegerConversion( float.class );
        registerBigIntegerConversion( Float.class );
        registerBigIntegerConversion( double.class );
        registerBigIntegerConversion( Double.class );

        //BigDecimal <> native types
        registerBigDecimalConversion( byte.class );
        registerBigDecimalConversion( Byte.class );
        registerBigDecimalConversion( short.class );
        registerBigDecimalConversion( Short.class );
        registerBigDecimalConversion( int.class );
        registerBigDecimalConversion( Integer.class );
        registerBigDecimalConversion( long.class );
        registerBigDecimalConversion( Long.class );
        registerBigDecimalConversion( float.class );
        registerBigDecimalConversion( Float.class );
        registerBigDecimalConversion( double.class );
        registerBigDecimalConversion( Double.class );

        //native types <> String
        registerToStringConversion( byte.class );
        registerToStringConversion( Byte.class );
        registerToStringConversion( short.class );
        registerToStringConversion( Short.class );
        registerToStringConversion( int.class );
        registerToStringConversion( Integer.class );
        registerToStringConversion( long.class );
        registerToStringConversion( Long.class );
        registerToStringConversion( float.class );
        registerToStringConversion( Float.class );
        registerToStringConversion( double.class );
        registerToStringConversion( Double.class );
        registerToStringConversion( boolean.class );
        registerToStringConversion( Boolean.class );
        register( char.class, String.class, new CharToStringConversion() );
        register( Character.class, String.class, new CharWrapperToStringConversion() );
        register( BigInteger.class, String.class, new BigIntegerToStringConversion( ) );
        register( BigDecimal.class, String.class, new BigDecimalToStringConversion( ) );

        registerJodaConversions();

        registerJava8TimeConversions();

        //misc.
        register( Enum.class, String.class, new EnumStringConversion() );
        register( Date.class, String.class, new DateToStringConversion() );
        register( BigDecimal.class, BigInteger.class, new BigDecimalToBigIntegerConversion() );
        register( Date.class, Time.class, new DateToSqlTimeConversion() );
        register( Date.class, java.sql.Date.class, new DateToSqlDateConversion() );
        register( Date.class, Timestamp.class, new DateToSqlTimestampConversion() );

        // java.util.Currency <~> String
        register( Currency.class, String.class, new CurrencyToStringConversion() );
    }

    private void registerNativeNumberConversions() {

        registerWideningOrSameConversion( byte.class, Byte.class, false );
        registerWideningOrSameConversion( byte.class, short.class, false );
        registerWideningOrSameConversion( byte.class, Short.class, false );
        registerWideningOrSameConversion( byte.class, int.class, false );
        registerWideningOrSameConversion( byte.class, Integer.class, false );
        registerWideningOrSameConversion( byte.class, long.class, false );
        registerWideningOrSameConversion( byte.class, Long.class, false );
        registerWideningOrSameConversion( byte.class, float.class, false );
        registerWideningOrSameConversion( byte.class, Float.class, false );
        registerWideningOrSameConversion( byte.class, double.class, false );
        registerWideningOrSameConversion( byte.class, Double.class, false );

        registerWideningOrSameConversion( Byte.class, byte.class, false );
        registerWideningOrSameConversion( Byte.class, short.class, false );
        registerWideningOrSameConversion( Byte.class, Short.class, false );
        registerWideningOrSameConversion( Byte.class, int.class, false );
        registerWideningOrSameConversion( Byte.class, Integer.class, false );
        registerWideningOrSameConversion( Byte.class, long.class, false );
        registerWideningOrSameConversion( Byte.class, Long.class, false );
        registerWideningOrSameConversion( Byte.class, float.class, false );
        registerWideningOrSameConversion( Byte.class, Float.class, false );
        registerWideningOrSameConversion( Byte.class, double.class, false );
        registerWideningOrSameConversion( Byte.class, Double.class, false );

        registerNarrowingConversion( short.class, byte.class, false );
        registerNarrowingConversion( short.class, Byte.class, false );
        registerWideningOrSameConversion( short.class, Short.class, false );
        registerWideningOrSameConversion( short.class, int.class, false );
        registerWideningOrSameConversion( short.class, Integer.class, false );
        registerWideningOrSameConversion( short.class, long.class, false );
        registerWideningOrSameConversion( short.class, Long.class, false );
        registerWideningOrSameConversion( short.class, float.class, false );
        registerWideningOrSameConversion( short.class, Float.class, false );
        registerWideningOrSameConversion( short.class, double.class, false );
        registerWideningOrSameConversion( short.class, Double.class, false );

        registerNarrowingConversion( Short.class, byte.class, false );
        registerNarrowingConversion( Short.class, Byte.class, false );
        registerWideningOrSameConversion( Short.class, short.class, false );
        registerWideningOrSameConversion( Short.class, int.class, false );
        registerWideningOrSameConversion( Short.class, Integer.class, false );
        registerWideningOrSameConversion( Short.class, long.class, false );
        registerWideningOrSameConversion( Short.class, Long.class, false );
        registerWideningOrSameConversion( Short.class, float.class, false );
        registerWideningOrSameConversion( Short.class, Float.class, false );
        registerWideningOrSameConversion( Short.class, double.class, false );
        registerWideningOrSameConversion( Short.class, Double.class, false );

        registerNarrowingConversion( int.class, byte.class, false );
        registerNarrowingConversion( int.class, Byte.class, false );
        registerNarrowingConversion( int.class, short.class, false );
        registerNarrowingConversion( int.class, Short.class, false );
        registerWideningOrSameConversion( int.class, Integer.class, false );
        registerWideningOrSameConversion( int.class, long.class, false );
        registerWideningOrSameConversion( int.class, Long.class, false );
        registerWideningOrSameConversion( int.class, float.class, true );
        registerWideningOrSameConversion( int.class, Float.class, true );
        registerWideningOrSameConversion( int.class, double.class, false );
        registerWideningOrSameConversion( int.class, Double.class, false );

        registerNarrowingConversion( Integer.class, byte.class, false );
        registerNarrowingConversion( Integer.class, Byte.class, false );
        registerNarrowingConversion( Integer.class, short.class, false );
        registerNarrowingConversion( Integer.class, Short.class, false );
        registerWideningOrSameConversion( Integer.class, int.class, false );
        registerWideningOrSameConversion( Integer.class, long.class, false );
        registerWideningOrSameConversion( Integer.class, Long.class, false );
        registerWideningOrSameConversion( Integer.class, float.class, true );
        registerWideningOrSameConversion( Integer.class, Float.class, true );
        registerWideningOrSameConversion( Integer.class, double.class, false );
        registerWideningOrSameConversion( Integer.class, Double.class, false );

        registerNarrowingConversion( long.class, byte.class, false );
        registerNarrowingConversion( long.class, Byte.class, false );
        registerNarrowingConversion( long.class, short.class, false );
        registerNarrowingConversion( long.class, Short.class, false );
        registerNarrowingConversion( long.class, int.class, false );
        registerNarrowingConversion( long.class, Integer.class, false );
        registerWideningOrSameConversion( long.class, Long.class, false );
        registerWideningOrSameConversion( long.class, float.class, true );
        registerWideningOrSameConversion( long.class, Float.class, true );
        registerWideningOrSameConversion( long.class, double.class, true );
        registerWideningOrSameConversion( long.class, Double.class, true );

        registerNarrowingConversion( Long.class, byte.class, false );
        registerNarrowingConversion( Long.class, Byte.class, false );
        registerNarrowingConversion( Long.class, short.class, false );
        registerNarrowingConversion( Long.class, Short.class, false );
        registerNarrowingConversion( Long.class, int.class, false );
        registerNarrowingConversion( Long.class, Integer.class, false );
        registerWideningOrSameConversion( Long.class, long.class, false );
        registerWideningOrSameConversion( Long.class, float.class, true );
        registerWideningOrSameConversion( Long.class, Float.class, true );
        registerWideningOrSameConversion( Long.class, double.class, true );
        registerWideningOrSameConversion( Long.class, Double.class, true );

        registerNarrowingConversion( float.class, byte.class, true );
        registerNarrowingConversion( float.class, Byte.class, true );
        registerNarrowingConversion( float.class, short.class, true );
        registerNarrowingConversion( float.class, Short.class, true );
        registerNarrowingConversion( float.class, int.class, true );
        registerNarrowingConversion( float.class, Integer.class, true );
        registerNarrowingConversion( float.class, long.class, true );
        registerNarrowingConversion( float.class, Long.class, true );
        registerWideningOrSameConversion( float.class, Float.class, false );
        registerWideningOrSameConversion( float.class, double.class, false );
        registerWideningOrSameConversion( float.class, Double.class, false );

        registerNarrowingConversion( Float.class, byte.class, true );
        registerNarrowingConversion( Float.class, Byte.class, true );
        registerNarrowingConversion( Float.class, short.class, true );
        registerNarrowingConversion( Float.class, Short.class, true );
        registerNarrowingConversion( Float.class, int.class, true );
        registerNarrowingConversion( Float.class, Integer.class, true );
        registerNarrowingConversion( Float.class, long.class, true );
        registerNarrowingConversion( Float.class, Long.class, true );
        registerWideningOrSameConversion( Float.class, float.class, false );
        registerWideningOrSameConversion( Float.class, double.class, false );
        registerWideningOrSameConversion( Float.class, Double.class, false );

        registerNarrowingConversion( double.class, byte.class, true );
        registerNarrowingConversion( double.class, Byte.class, true );
        registerNarrowingConversion( double.class, short.class, true );
        registerNarrowingConversion( double.class, Short.class, true );
        registerNarrowingConversion( double.class, int.class, true );
        registerNarrowingConversion( double.class, Integer.class, true );
        registerNarrowingConversion( double.class, long.class, true );
        registerNarrowingConversion( double.class, Long.class, true );
        registerNarrowingConversion( double.class, float.class, true );
        registerNarrowingConversion( double.class, Float.class, true );
        registerWideningOrSameConversion( double.class, Double.class, false );

        registerNarrowingConversion( Double.class, byte.class, true );
        registerNarrowingConversion( Double.class, Byte.class, true );
        registerNarrowingConversion( Double.class, short.class, true );
        registerNarrowingConversion( Double.class, Short.class, true );
        registerNarrowingConversion( Double.class, int.class, true );
        registerNarrowingConversion( Double.class, Integer.class, true );
        registerNarrowingConversion( Double.class, long.class, true );
        registerNarrowingConversion( Double.class, Long.class, true );
        registerNarrowingConversion( Double.class, float.class, true );
        registerNarrowingConversion( Double.class, Float.class, true );
        registerWideningOrSameConversion( Double.class, double.class, false );

        registerWideningOrSameConversion( Character.class, char.class, false );
        registerWideningOrSameConversion( char.class, Character.class, false );

    }

    private void registerJodaConversions() {
        if ( !isJodaTimeAvailable() ) {
            return;
        }

        // Joda to String
        register( JodaTimeConstants.DATE_TIME_FQN, String.class, new JodaDateTimeToStringConversion() );
        register( JodaTimeConstants.LOCAL_DATE_FQN, String.class, new JodaLocalDateToStringConversion() );
        register( JodaTimeConstants.LOCAL_DATE_TIME_FQN, String.class, new JodaLocalDateTimeToStringConversion() );
        register( JodaTimeConstants.LOCAL_TIME_FQN, String.class, new JodaLocalTimeToStringConversion() );

        // Joda to Date
        register( JodaTimeConstants.DATE_TIME_FQN, Date.class, new JodaTimeToDateConversion() );
        register( JodaTimeConstants.LOCAL_DATE_FQN, Date.class, new JodaTimeToDateConversion() );
        register( JodaTimeConstants.LOCAL_DATE_TIME_FQN, Date.class, new JodaTimeToDateConversion() );

        // Joda to Calendar
        register( JodaTimeConstants.DATE_TIME_FQN, Calendar.class, new JodaDateTimeToCalendarConversion() );
    }

    private void registerJava8TimeConversions() {
        if ( !isJava8TimeAvailable() ) {
            return;
        }

        // Java 8 time to String
        register( JavaTimeConstants.ZONED_DATE_TIME_FQN, String.class, new JavaZonedDateTimeToStringConversion() );
        register( JavaTimeConstants.LOCAL_DATE_FQN, String.class, new JavaLocalDateToStringConversion() );
        register( JavaTimeConstants.LOCAL_DATE_TIME_FQN, String.class, new JavaLocalDateTimeToStringConversion() );
        register( JavaTimeConstants.LOCAL_TIME_FQN, String.class, new JavaLocalTimeToStringConversion() );

        // Java 8 to Date
        register( JavaTimeConstants.ZONED_DATE_TIME_FQN, Date.class, new JavaZonedDateTimeToDateConversion() );
        register( JavaTimeConstants.LOCAL_DATE_TIME_FQN, Date.class, new JavaLocalDateTimeToDateConversion() );
        register( JavaTimeConstants.LOCAL_DATE_FQN, Date.class, new JavaLocalDateToDateConversion() );
        register( JavaTimeConstants.LOCAL_DATE_FQN, java.sql.Date.class, new JavaLocalDateToSqlDateConversion() );
        register( JavaTimeConstants.INSTANT, Date.class, new JavaInstantToDateConversion() );

    }

    private boolean isJodaTimeAvailable() {
        return typeFactory.isTypeAvailable( JodaTimeConstants.DATE_TIME_FQN );
    }

    private boolean isJava8TimeAvailable() {
        return typeFactory.isTypeAvailable( JavaTimeConstants.ZONED_DATE_TIME_FQN );
    }

    private void registerWideningOrSameConversion(Class<?> sourceClass, Class<?> targetClass, boolean precisionLoss) {
        ConversionProvider conversion;
        if ( sourceClass.isPrimitive() && targetClass.isPrimitive() ) {
            conversion = new PrimitiveToPrimitiveWideningConversion();
        }
        else if ( sourceClass.isPrimitive() && !targetClass.isPrimitive() ) {
            conversion = new PrimitiveToWrapperWideningConversion( sourceClass, targetClass );
        }
        else if ( !sourceClass.isPrimitive() && targetClass.isPrimitive() ) {
            conversion = new WrapperToPrimitiveWideningConversion( targetClass );
        }
        else {
            conversion = new WrapperToWrapperWideningConversion( sourceClass, targetClass );
        }
        Type sourceType = typeFactory.getType( sourceClass );
        Type targetType = typeFactory.getType( targetClass );
        conversions.put( new Key( sourceType, targetType ), conversion );
    }

    private void registerNarrowingConversion(Class<?> sourceClass, Class<?> targetClass, boolean precisionLoss ) {
        ConversionProvider conversion;
        if ( sourceClass.isPrimitive() && targetClass.isPrimitive() ) {
            conversion = new PrimitiveToPrimitiveNarrowingConversion( targetClass );
        }
        else if ( sourceClass.isPrimitive() && !targetClass.isPrimitive() ) {
            conversion = new PrimitiveToWrapperNarrowingConversion( targetClass );
        }
        else if ( !sourceClass.isPrimitive() && targetClass.isPrimitive() ) {
            conversion = new WrapperToPrimitiveNarrowingConversion( targetClass );
        }
        else {
            conversion = new WrapperToWrapperNarrowingConversion( targetClass );
        }
        Type sourceType = typeFactory.getType( sourceClass );
        Type targetType = typeFactory.getType( targetClass );
        conversions.put( new Key( sourceType, targetType ), conversion );
    }

    private void registerToStringConversion(Class<?> sourceType) {
        if ( sourceType.isPrimitive() ) {
            register( sourceType, String.class, new PrimitiveToStringConversion( sourceType ) );
        }
        else {
            register( sourceType, String.class, new WrapperToStringConversion( sourceType ) );
        }
    }

    private void registerBigIntegerConversion(Class<?> targetClass) {
        ConversionProvider wideningConversion;
        ConversionProvider narrowingConversion;
        if ( targetClass.isPrimitive() ) {
            wideningConversion = new BigIntegerToPrimitiveConversion( targetClass );
            narrowingConversion = new PrimitiveToBigIntegerConversion( targetClass );
        }
        else {
            wideningConversion = new BigIntegerToWrapperConversion( targetClass );
            narrowingConversion = new WrapperToBigIntegerConversion( targetClass );
        }
        Type bitIntegerType = typeFactory.getType( BigInteger.class );
        Type type = typeFactory.getType( targetClass );
        conversions.put( new Key( bitIntegerType, type ), wideningConversion );
        conversions.put( new Key( type, bitIntegerType ), narrowingConversion );
    }

    private void registerBigDecimalConversion(Class<?> targetClass) {
        ConversionProvider wideningConversion;
        ConversionProvider narrowingConversion;
        if ( targetClass.isPrimitive() ) {
            wideningConversion = new BigDecimalToPrimitiveConversion( targetClass );
            narrowingConversion = new PrimitiveToBigDecimalConversion();
        }
        else {
            wideningConversion = new BigDecimalToWrapperConversion( targetClass );
            narrowingConversion = new WrapperToBigDecimalConversion();
        }
        Type bitDecimalType = typeFactory.getType( BigDecimal.class );
        Type type = typeFactory.getType( targetClass );
        conversions.put( new Key( bitDecimalType, type ), wideningConversion );
        conversions.put( new Key( type, bitDecimalType ), narrowingConversion );
    }

    private void register(Class<?> sourceClass, Class<?> targetClass, ConversionProvider conversion) {
        Type sourceType = typeFactory.getType( sourceClass );
        Type targetType = typeFactory.getType( targetClass );

        conversions.put( new Key( sourceType, targetType ), conversion );
        conversions.put( new Key( targetType, sourceType ), reverse( conversion ) );
    }

    private void register(String sourceTypeName, Class<?> targetClass, ConversionProvider conversion) {
        Type sourceType = typeFactory.getType( sourceTypeName );
        Type targetType = typeFactory.getType( targetClass );

        conversions.put( new Key( sourceType, targetType ), conversion );
        conversions.put( new Key( targetType, sourceType ), reverse( conversion ) );
    }

    public ConversionProvider getConversion(Type sourceType, Type targetType) {
        if ( sourceType.isEnumType() && targetType.equals( stringType ) ) {
            sourceType = enumType;
        }
        else if ( targetType.isEnumType() && sourceType.equals( stringType ) ) {
            targetType = enumType;
        }

        return conversions.get( new Key( sourceType, targetType ) );
    }

    private static class Key {
        private final Type sourceType;
        private final Type targetType;

        private Key(Type sourceType, Type targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public String toString() {
            return "Key [sourceType=" + sourceType + ", targetType="
                + targetType + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ( ( sourceType == null ) ? 0 : sourceType.hashCode() );
            result = prime * result + ( ( targetType == null ) ? 0 : targetType.hashCode() );
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if ( this == obj ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            Key other = (Key) obj;
            if ( sourceType == null ) {
                if ( other.sourceType != null ) {
                    return false;
                }
            }
            else if ( !sourceType.equals( other.sourceType ) ) {
                return false;
            }
            if ( targetType == null ) {
                if ( other.targetType != null ) {
                    return false;
                }
            }
            else if ( !targetType.equals( other.targetType ) ) {
                return false;
            }
            return true;
        }
    }
}
