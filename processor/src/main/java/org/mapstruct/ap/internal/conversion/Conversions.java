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

        registerWideningOrSameConversion( boolean.class, Boolean.class );
        registerWideningOrSameConversion( Boolean.class, boolean.class );

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

        registerWideningOrSameConversion( byte.class, Byte.class );
        registerWideningOrSameConversion( byte.class, short.class );
        registerWideningOrSameConversion( byte.class, Short.class );
        registerWideningOrSameConversion( byte.class, int.class );
        registerWideningOrSameConversion( byte.class, Integer.class );
        registerWideningOrSameConversion( byte.class, long.class );
        registerWideningOrSameConversion( byte.class, Long.class );
        registerWideningOrSameConversion( byte.class, float.class );
        registerWideningOrSameConversion( byte.class, Float.class );
        registerWideningOrSameConversion( byte.class, double.class );
        registerWideningOrSameConversion( byte.class, Double.class );

        registerWideningOrSameConversion( Byte.class, byte.class );
        registerWideningOrSameConversion( Byte.class, short.class );
        registerWideningOrSameConversion( Byte.class, Short.class );
        registerWideningOrSameConversion( Byte.class, int.class );
        registerWideningOrSameConversion( Byte.class, Integer.class );
        registerWideningOrSameConversion( Byte.class, long.class );
        registerWideningOrSameConversion( Byte.class, Long.class );
        registerWideningOrSameConversion( Byte.class, float.class );
        registerWideningOrSameConversion( Byte.class, Float.class );
        registerWideningOrSameConversion( Byte.class, double.class );
        registerWideningOrSameConversion( Byte.class, Double.class );

        registerNarrowingConversion( short.class, byte.class );
        registerNarrowingConversion( short.class, Byte.class );
        registerWideningOrSameConversion( short.class, Short.class );
        registerWideningOrSameConversion( short.class, int.class );
        registerWideningOrSameConversion( short.class, Integer.class );
        registerWideningOrSameConversion( short.class, long.class );
        registerWideningOrSameConversion( short.class, Long.class );
        registerWideningOrSameConversion( short.class, float.class );
        registerWideningOrSameConversion( short.class, Float.class );
        registerWideningOrSameConversion( short.class, double.class );
        registerWideningOrSameConversion( short.class, Double.class );

        registerNarrowingConversion( Short.class, byte.class );
        registerNarrowingConversion( Short.class, Byte.class );
        registerWideningOrSameConversion( Short.class, short.class );
        registerWideningOrSameConversion( Short.class, int.class );
        registerWideningOrSameConversion( Short.class, Integer.class );
        registerWideningOrSameConversion( Short.class, long.class );
        registerWideningOrSameConversion( Short.class, Long.class );
        registerWideningOrSameConversion( Short.class, float.class );
        registerWideningOrSameConversion( Short.class, Float.class );
        registerWideningOrSameConversion( Short.class, double.class );
        registerWideningOrSameConversion( Short.class, Double.class );

        registerNarrowingConversion( int.class, byte.class );
        registerNarrowingConversion( int.class, Byte.class );
        registerNarrowingConversion( int.class, short.class );
        registerNarrowingConversion( int.class, Short.class );
        registerWideningOrSameConversion( int.class, Integer.class );
        registerWideningOrSameConversion( int.class, long.class );
        registerWideningOrSameConversion( int.class, Long.class );
        registerWideningOrSameConversion( int.class, float.class );
        registerWideningOrSameConversion( int.class, Float.class );
        registerWideningOrSameConversion( int.class, double.class );
        registerWideningOrSameConversion( int.class, Double.class );

        registerNarrowingConversion( Integer.class, byte.class );
        registerNarrowingConversion( Integer.class, Byte.class );
        registerNarrowingConversion( Integer.class, short.class );
        registerNarrowingConversion( Integer.class, Short.class );
        registerWideningOrSameConversion( Integer.class, int.class );
        registerWideningOrSameConversion( Integer.class, long.class );
        registerWideningOrSameConversion( Integer.class, Long.class );
        registerWideningOrSameConversion( Integer.class, float.class );
        registerWideningOrSameConversion( Integer.class, Float.class );
        registerWideningOrSameConversion( Integer.class, double.class );
        registerWideningOrSameConversion( Integer.class, Double.class );

        registerNarrowingConversion( long.class, byte.class );
        registerNarrowingConversion( long.class, Byte.class );
        registerNarrowingConversion( long.class, short.class );
        registerNarrowingConversion( long.class, Short.class );
        registerNarrowingConversion( long.class, int.class );
        registerNarrowingConversion( long.class, Integer.class );
        registerWideningOrSameConversion( long.class, Long.class );
        registerWideningOrSameConversion( long.class, float.class );
        registerWideningOrSameConversion( long.class, Float.class );
        registerWideningOrSameConversion( long.class, double.class );
        registerWideningOrSameConversion( long.class, Double.class );

        registerNarrowingConversion( Long.class, byte.class );
        registerNarrowingConversion( Long.class, Byte.class );
        registerNarrowingConversion( Long.class, short.class );
        registerNarrowingConversion( Long.class, Short.class );
        registerNarrowingConversion( Long.class, int.class );
        registerNarrowingConversion( Long.class, Integer.class );
        registerWideningOrSameConversion( Long.class, long.class );
        registerWideningOrSameConversion( Long.class, float.class );
        registerWideningOrSameConversion( Long.class, Float.class );
        registerWideningOrSameConversion( Long.class, double.class );
        registerWideningOrSameConversion( Long.class, Double.class );

        registerNarrowingConversion( float.class, byte.class );
        registerNarrowingConversion( float.class, Byte.class );
        registerNarrowingConversion( float.class, short.class );
        registerNarrowingConversion( float.class, Short.class );
        registerNarrowingConversion( float.class, int.class );
        registerNarrowingConversion( float.class, Integer.class );
        registerNarrowingConversion( float.class, long.class );
        registerNarrowingConversion( float.class, Long.class );
        registerWideningOrSameConversion( float.class, Float.class );
        registerWideningOrSameConversion( float.class, double.class );
        registerWideningOrSameConversion( float.class, Double.class );

        registerNarrowingConversion( Float.class, byte.class );
        registerNarrowingConversion( Float.class, Byte.class );
        registerNarrowingConversion( Float.class, short.class );
        registerNarrowingConversion( Float.class, Short.class );
        registerNarrowingConversion( Float.class, int.class );
        registerNarrowingConversion( Float.class, Integer.class );
        registerNarrowingConversion( Float.class, long.class );
        registerNarrowingConversion( Float.class, Long.class );
        registerWideningOrSameConversion( Float.class, float.class );
        registerWideningOrSameConversion( Float.class, double.class );
        registerWideningOrSameConversion( Float.class, Double.class );

        registerNarrowingConversion( double.class, byte.class );
        registerNarrowingConversion( double.class, Byte.class );
        registerNarrowingConversion( double.class, short.class );
        registerNarrowingConversion( double.class, Short.class );
        registerNarrowingConversion( double.class, int.class );
        registerNarrowingConversion( double.class, Integer.class );
        registerNarrowingConversion( double.class, long.class );
        registerNarrowingConversion( double.class, Long.class );
        registerNarrowingConversion( double.class, float.class );
        registerNarrowingConversion( double.class, Float.class );
        registerWideningOrSameConversion( double.class, Double.class );

        registerNarrowingConversion( Double.class, byte.class );
        registerNarrowingConversion( Double.class, Byte.class );
        registerNarrowingConversion( Double.class, short.class );
        registerNarrowingConversion( Double.class, Short.class );
        registerNarrowingConversion( Double.class, int.class );
        registerNarrowingConversion( Double.class, Integer.class );
        registerNarrowingConversion( Double.class, long.class );
        registerNarrowingConversion( Double.class, Long.class );
        registerNarrowingConversion( Double.class, float.class );
        registerNarrowingConversion( Double.class, Float.class );
        registerWideningOrSameConversion( Double.class, double.class );

        registerWideningOrSameConversion( Character.class, char.class );
        registerWideningOrSameConversion( char.class, Character.class );

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

    private void registerWideningOrSameConversion(Class<?> sourceClass, Class<?> targetClass) {
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

    private void registerNarrowingConversion(Class<?> sourceClass, Class<?> targetClass) {
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
