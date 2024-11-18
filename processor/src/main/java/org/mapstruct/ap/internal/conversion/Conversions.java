/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

import static org.mapstruct.ap.internal.conversion.ReverseConversion.inverse;

/**
 * Holds built-in {@link ConversionProvider}s such as from {@code int} to {@code String}.
 *
 * @author Gunnar Morling
 */
public class Conversions {

    private final Map<Key, ConversionProvider> conversions = new HashMap<>();
    private final Type enumType;
    private final Type stringType;
    private final Type integerType;
    private final TypeFactory typeFactory;

    public Conversions(TypeFactory typeFactory) {
        this.typeFactory = typeFactory;

        this.enumType = typeFactory.getType( Enum.class );
        this.stringType = typeFactory.getType( String.class );
        this.integerType = typeFactory.getType( Integer.class );

        //native types <> native types, including wrappers
        registerNativeTypeConversion( byte.class, Byte.class );
        registerNativeTypeConversion( byte.class, short.class );
        registerNativeTypeConversion( byte.class, Short.class );
        registerNativeTypeConversion( byte.class, int.class );
        registerNativeTypeConversion( byte.class, Integer.class );
        registerNativeTypeConversion( byte.class, long.class );
        registerNativeTypeConversion( byte.class, Long.class );
        registerNativeTypeConversion( byte.class, float.class );
        registerNativeTypeConversion( byte.class, Float.class );
        registerNativeTypeConversion( byte.class, double.class );
        registerNativeTypeConversion( byte.class, Double.class );

        registerNativeTypeConversion( Byte.class, short.class );
        registerNativeTypeConversion( Byte.class, Short.class );
        registerNativeTypeConversion( Byte.class, int.class );
        registerNativeTypeConversion( Byte.class, Integer.class );
        registerNativeTypeConversion( Byte.class, long.class );
        registerNativeTypeConversion( Byte.class, Long.class );
        registerNativeTypeConversion( Byte.class, float.class );
        registerNativeTypeConversion( Byte.class, Float.class );
        registerNativeTypeConversion( Byte.class, double.class );
        registerNativeTypeConversion( Byte.class, Double.class );

        registerNativeTypeConversion( short.class, Short.class );
        registerNativeTypeConversion( short.class, int.class );
        registerNativeTypeConversion( short.class, Integer.class );
        registerNativeTypeConversion( short.class, long.class );
        registerNativeTypeConversion( short.class, Long.class );
        registerNativeTypeConversion( short.class, float.class );
        registerNativeTypeConversion( short.class, Float.class );
        registerNativeTypeConversion( short.class, double.class );
        registerNativeTypeConversion( short.class, Double.class );

        registerNativeTypeConversion( Short.class, int.class );
        registerNativeTypeConversion( Short.class, Integer.class );
        registerNativeTypeConversion( Short.class, long.class );
        registerNativeTypeConversion( Short.class, Long.class );
        registerNativeTypeConversion( Short.class, float.class );
        registerNativeTypeConversion( Short.class, Float.class );
        registerNativeTypeConversion( Short.class, double.class );
        registerNativeTypeConversion( Short.class, Double.class );

        registerNativeTypeConversion( int.class, Integer.class );
        registerNativeTypeConversion( int.class, long.class );
        registerNativeTypeConversion( int.class, Long.class );
        registerNativeTypeConversion( int.class, float.class );
        registerNativeTypeConversion( int.class, Float.class );
        registerNativeTypeConversion( int.class, double.class );
        registerNativeTypeConversion( int.class, Double.class );

        registerNativeTypeConversion( Integer.class, long.class );
        registerNativeTypeConversion( Integer.class, Long.class );
        registerNativeTypeConversion( Integer.class, float.class );
        registerNativeTypeConversion( Integer.class, Float.class );
        registerNativeTypeConversion( Integer.class, double.class );
        registerNativeTypeConversion( Integer.class, Double.class );

        registerNativeTypeConversion( long.class, Long.class );
        registerNativeTypeConversion( long.class, float.class );
        registerNativeTypeConversion( long.class, Float.class );
        registerNativeTypeConversion( long.class, double.class );
        registerNativeTypeConversion( long.class, Double.class );

        registerNativeTypeConversion( Long.class, float.class );
        registerNativeTypeConversion( Long.class, Float.class );
        registerNativeTypeConversion( Long.class, double.class );
        registerNativeTypeConversion( Long.class, Double.class );

        registerNativeTypeConversion( float.class, Float.class );
        registerNativeTypeConversion( float.class, double.class );
        registerNativeTypeConversion( float.class, Double.class );

        registerNativeTypeConversion( Float.class, double.class );
        registerNativeTypeConversion( Float.class, Double.class );

        registerNativeTypeConversion( double.class, Double.class );

        registerNativeTypeConversion( boolean.class, Boolean.class );
        registerNativeTypeConversion( char.class, Character.class );

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
        register( BigInteger.class, String.class, new BigIntegerToStringConversion() );
        register( BigDecimal.class, String.class, new BigDecimalToStringConversion() );
        register( StringBuilder.class, String.class, new StringBuilderToStringConversion() );

        registerJodaConversions();

        registerJava8TimeConversions();

        //misc.
        register( Enum.class, String.class, new EnumStringConversion() );
        register( Enum.class, Integer.class, new EnumToIntegerConversion() );
        register( Enum.class, int.class, new EnumToIntegerConversion() );
        register( Date.class, String.class, new DateToStringConversion() );
        register( BigDecimal.class, BigInteger.class, new BigDecimalToBigIntegerConversion() );

        registerJavaTimeSqlConversions();

        // java.util.Currency <~> String
        register( Currency.class, String.class, new CurrencyToStringConversion() );

        register( UUID.class, String.class, new UUIDToStringConversion() );
        register( Locale.class, String.class, new LocaleToStringConversion() );

        registerURLConversion();
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

        // Java 8 time to String
        register( ZonedDateTime.class, String.class, new JavaZonedDateTimeToStringConversion() );
        register( LocalDate.class, String.class, new JavaLocalDateToStringConversion() );
        register( LocalDateTime.class, String.class, new JavaLocalDateTimeToStringConversion() );
        register( LocalTime.class, String.class, new JavaLocalTimeToStringConversion() );
        register( Instant.class, String.class, new StaticParseToStringConversion() );
        register( Period.class, String.class, new StaticParseToStringConversion() );
        register( Duration.class, String.class, new StaticParseToStringConversion() );

        // Java 8 time to Date
        register( ZonedDateTime.class, Date.class, new JavaZonedDateTimeToDateConversion() );
        register( LocalDateTime.class, Date.class, new JavaLocalDateTimeToDateConversion() );
        register( LocalDate.class, Date.class, new JavaLocalDateToDateConversion() );
        register( Instant.class, Date.class, new JavaInstantToDateConversion() );

        // Java 8 time
        register( LocalDateTime.class, LocalDate.class, new JavaLocalDateTimeToLocalDateConversion() );

    }

    private void registerJavaTimeSqlConversions() {
        if ( isJavaSqlAvailable() ) {
            register( LocalDate.class, java.sql.Date.class, new JavaLocalDateToSqlDateConversion() );

            register( Date.class, Time.class, new DateToSqlTimeConversion() );
            register( Date.class, java.sql.Date.class, new DateToSqlDateConversion() );
            register( Date.class, Timestamp.class, new DateToSqlTimestampConversion() );
        }
    }

    private boolean isJodaTimeAvailable() {
        return typeFactory.isTypeAvailable( JodaTimeConstants.DATE_TIME_FQN );
    }

    private boolean isJavaSqlAvailable() {
        return typeFactory.isTypeAvailable( "java.sql.Date" );
    }

    private void registerNativeTypeConversion(Class<?> sourceType, Class<?> targetType) {
        if ( sourceType.isPrimitive() && targetType.isPrimitive() ) {
            register( sourceType, targetType, new PrimitiveToPrimitiveConversion( sourceType ) );
        }
        else if ( sourceType.isPrimitive() ) {
            register( sourceType, targetType, new PrimitiveToWrapperConversion( sourceType, targetType ) );
        }
        else if ( targetType.isPrimitive() ) {
            register( sourceType, targetType, inverse( new PrimitiveToWrapperConversion( targetType, sourceType ) ) );
        }
        else {
            register( sourceType, targetType, new WrapperToWrapperConversion( sourceType, targetType ) );
        }
    }

    private void registerToStringConversion(Class<?> sourceType) {
        if ( sourceType.isPrimitive() ) {
            register( sourceType, String.class, new PrimitiveToStringConversion( sourceType ) );
        }
        else {
            register( sourceType, String.class, new WrapperToStringConversion( sourceType ) );
        }
    }

    private void registerBigIntegerConversion(Class<?> targetType) {
        if ( targetType.isPrimitive() ) {
            register( BigInteger.class, targetType, new BigIntegerToPrimitiveConversion( targetType ) );
        }
        else {
            register( BigInteger.class, targetType, new BigIntegerToWrapperConversion( targetType ) );
        }
    }

    private void registerBigDecimalConversion(Class<?> targetType) {
        if ( targetType.isPrimitive() ) {
            register( BigDecimal.class, targetType, new BigDecimalToPrimitiveConversion( targetType ) );
        }
        else {
            register( BigDecimal.class, targetType, new BigDecimalToWrapperConversion( targetType ) );
        }
    }

    private void registerURLConversion() {
        if ( isJavaURLAvailable() ) {
            register( URL.class, String.class, new URLToStringConversion() );
        }
    }

    private boolean isJavaURLAvailable() {
        return typeFactory.isTypeAvailable( "java.net.URL" );
    }

    private void register(Class<?> sourceClass, Class<?> targetClass, ConversionProvider conversion) {
        Type sourceType = typeFactory.getType( sourceClass );
        Type targetType = typeFactory.getType( targetClass );

        conversions.put( new Key( sourceType, targetType ), conversion );
        conversions.put( new Key( targetType, sourceType ), inverse( conversion ) );
    }

    private void register(String sourceTypeName, Class<?> targetClass, ConversionProvider conversion) {
        Type sourceType = typeFactory.getType( sourceTypeName );
        Type targetType = typeFactory.getType( targetClass );

        conversions.put( new Key( sourceType, targetType ), conversion );
        conversions.put( new Key( targetType, sourceType ), inverse( conversion ) );
    }

    public ConversionProvider getConversion(Type sourceType, Type targetType) {
        if ( sourceType.isEnumType() &&
                ( targetType.equals( stringType ) ||
                  targetType.getBoxedEquivalent().equals( integerType ) )
        ) {
            sourceType = enumType;
        }
        else if ( targetType.isEnumType() &&
                ( sourceType.equals( stringType ) ||
                  sourceType.getBoxedEquivalent().equals( integerType ) )
        ) {
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

            if ( !Objects.equals( sourceType, other.sourceType ) ) {
                return false;
            }

            return Objects.equals( targetType, other.targetType );
        }
    }
}
