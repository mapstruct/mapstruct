/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.util.JavaTimeConstants;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

/**
 * Methods mainly used in {@link org.mapstruct.ap.internal.conversion.SimpleConversion SimpleConversion} classes, e. g.
 * to get the correct String representation of various types that could be used in generated sources.
 *
 * @author Christian Bandowski
 */
public final class ConversionUtils {
    private ConversionUtils() {
    }

    /**
     * Name for the given {@code canonicalName}.
     *
     * @param conversionContext Conversion context
     * @param canonicalName Canonical name of the type
     *
     * @return Name or fully-qualified name.
     */
    private static String typeReferenceName(ConversionContext conversionContext, String canonicalName) {
        return conversionContext.getTypeFactory().getType( canonicalName ).getReferenceName();
    }

    /**
     * Name for the given {@code canonicalName}.
     *
     * @param conversionContext Conversion context
     * @param type Type
     *
     * @return Name or fully-qualified name.
     */
    private static String typeReferenceName(ConversionContext conversionContext, Class<?> type) {
        return conversionContext.getTypeFactory().getType( type ).getReferenceName();
    }

    /**
     * Name for {@link java.math.BigDecimal}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String bigDecimal(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, BigDecimal.class );
    }

    /**
     * Name for {@link java.math.BigInteger}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String bigInteger(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, BigInteger.class );
    }

    /**
     * Name for {@link java.util.Locale}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String locale(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, Locale.class );
    }

    /**
     * Name for {@link java.util.Currency}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String currency(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, Currency.class );
    }

    /**
     * Name for {@link java.sql.Date}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String sqlDate(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, java.sql.Date.class );
    }

    /**
     * Name for {@link java.sql.Time}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String time(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, Time.class );
    }

    /**
     * Name for {@link java.sql.Timestamp}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String timestamp(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, Timestamp.class );
    }

    /**
     * Name for {@link java.text.DecimalFormat}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String decimalFormat(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, DecimalFormat.class );
    }

    /**
     * Name for {@link java.text.SimpleDateFormat}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String simpleDateFormat(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, SimpleDateFormat.class );
    }

    /**
     * Name for {@link java.util.Date}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String date(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, java.util.Date.class );
    }

    /**
     * Name for {@link java.time.ZoneOffset}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String zoneOffset(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, JavaTimeConstants.ZONE_OFFSET_FQN );
    }

    /**
     * Name for {@link java.time.ZoneId}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String zoneId(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, JavaTimeConstants.ZONE_ID_FQN );
    }

    /**
     * Name for {@link java.time.LocalDateTime}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String localDateTime(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, JavaTimeConstants.LOCAL_DATE_TIME_FQN );
    }

    /**
     * Name for {@link java.time.ZonedDateTime}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String zonedDateTime(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, JavaTimeConstants.ZONED_DATE_TIME_FQN );
    }

    /**
     * Name for {@link java.time.format.DateTimeFormatter}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String dateTimeFormatter(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, JavaTimeConstants.DATE_TIME_FORMATTER_FQN );
    }

    /**
     * Name for {@code org.joda.time.format.DateTimeFormat}.
     *
     * @param conversionContext Conversion context
     *
     * @return Name or fully-qualified name.
     */
    public static String dateTimeFormat(ConversionContext conversionContext) {
        return typeReferenceName( conversionContext, JodaTimeConstants.DATE_TIME_FORMAT_FQN );
    }
}
