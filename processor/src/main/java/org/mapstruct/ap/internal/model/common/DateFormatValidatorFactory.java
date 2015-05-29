/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.common;

import org.mapstruct.ap.internal.util.JavaTimeConstants;
import org.mapstruct.ap.internal.util.JodaTimeConstants;
import org.mapstruct.ap.internal.util.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
 * Factory for {@link DateFormatValidator}. <p> Based on the types of source / target type  a specific {@link
 * DateFormatValidator} will be instantiated. <br /> <ul> <li>Joda Time</li> <li>Java 8 Time and</li>
 * <li>java.util.Date</li> </ul> are supported. </p>
 */
final class DateFormatValidatorFactory {

    private static final String JAVA_UTIL_DATE = "java.util.Date";

    private static final String JAVA_TIME_FORMAT_DATE_TIME_FORMATTER = "java.time.format.DateTimeFormatter";

    private static final String OF_PATTERN = "ofPattern";

    private static final String ORG_JODA_TIME_FORMAT_DATE_TIME_FORMAT = "org.joda.time.format.DateTimeFormat";

    private static final String FOR_PATTERN = "forPattern";
    private static final String JAVAX_XML_DATATYPE_XMLGREGORIAN_CALENDAR = "javax.xml.datatype.XMLGregorianCalendar";

    private DateFormatValidatorFactory() {
    }

    /**
     * Create a new {@link DateFormatValidator} based on source/target type of a type conversion. Theirs typenames will
     * be compared against the supported types to determine a validator.
     *
     * @param sourceType The source type
     * @param targetType The target type
     * @return a new {@link DateFormatValidator}
     */
    public static DateFormatValidator forTypes(final Type sourceType, final Type targetType) {
        DateFormatValidator dateFormatValidator;

        if ( isJavaUtilDateSupposed( sourceType, targetType ) ) {
            dateFormatValidator = new SimpleDateFormatValidator();
        }
        else if ( isXmlGregorianCalendarSupposedToBeMapped( sourceType, targetType ) ) {
            dateFormatValidator = new SimpleDateFormatValidator();
        }
        else if ( isJava8DateTimeSupposed( sourceType, targetType ) ) {
            dateFormatValidator = new JavaDateTimeDateFormatValidator();
        }
        else if ( isJodaDateTimeSupposed( sourceType, targetType ) ) {
            dateFormatValidator = new JodaTimeDateFormatValidator();
        }
        else {
            dateFormatValidator = new DateFormatValidator() {
                @Override
                public DateFormatValidationResult validate(String dateFormat) {
                    return new DateFormatValidationResult( true, Message.GENERAL_UNSUPPORTED_DATE_FORMAT_CHECK,
                                                           sourceType, targetType );
                }
            };
        }
        return dateFormatValidator;

    }

    private static boolean isXmlGregorianCalendarSupposedToBeMapped(Type sourceType, Type targetType) {
        return typesEqualsOneOf(
                        sourceType, targetType, JAVAX_XML_DATATYPE_XMLGREGORIAN_CALENDAR );
    }

    private static boolean isJodaDateTimeSupposed(Type sourceType, Type targetType) {
        return typesEqualsOneOf(
                        sourceType,
                        targetType,
                        JodaTimeConstants.LOCAL_DATE_FQN,
                        JodaTimeConstants.LOCAL_TIME_FQN,
                        JodaTimeConstants.LOCAL_DATE_TIME_FQN,
                        JodaTimeConstants.DATE_TIME_FQN );
    }

    private static boolean isJava8DateTimeSupposed(Type sourceType, Type targetType) {
        return typesEqualsOneOf(
                        sourceType,
                        targetType,
                        JavaTimeConstants.LOCAL_DATE_FQN,
                        JavaTimeConstants.LOCAL_TIME_FQN,
                        JavaTimeConstants.LOCAL_DATE_TIME_FQN,
                        JavaTimeConstants.ZONED_DATE_TIME_FQN );
    }

    private static boolean isJavaUtilDateSupposed(Type sourceType, Type targetType) {
        return JAVA_UTIL_DATE.equals( sourceType.getFullyQualifiedName() ) || JAVA_UTIL_DATE.equals(
                        targetType.getFullyQualifiedName() );
    }

    private static boolean typesEqualsOneOf(Type sourceType, Type targetType, String... typeNames) {
        for ( String typeName : typeNames ) {
            if ( typeName.equals( sourceType.getFullyQualifiedName() )
                            || typeName.equals( targetType.getFullyQualifiedName() ) ) {
                return true;
            }
        }
        return false;
    }

    private static class JavaDateTimeDateFormatValidator implements DateFormatValidator {
        @Override
        public DateFormatValidationResult validate(String dateFormat) {
            try {
                Class<?> aClass = Class.forName( JAVA_TIME_FORMAT_DATE_TIME_FORMATTER );
                Method ofPatternMethod = aClass.getMethod( OF_PATTERN, String.class );
                ofPatternMethod.invoke( aClass, dateFormat );
                return validDateFormat( dateFormat );
            }
            catch ( InvocationTargetException e ) {
                return invalidDateFormat( dateFormat, e.getCause() );
            }
            catch ( Exception e ) {
                return invalidDateFormat( dateFormat, e );
            }
        }
    }

    private static class JodaTimeDateFormatValidator implements DateFormatValidator {

        @Override
        public DateFormatValidationResult validate(String dateFormat) {
            try {
                Class<?> aClass = Class.forName( ORG_JODA_TIME_FORMAT_DATE_TIME_FORMAT );
                Method forPatternMethod = aClass.getMethod( FOR_PATTERN, String.class );
                forPatternMethod.invoke( aClass, dateFormat );
                return validDateFormat( dateFormat );
            }
            catch ( InvocationTargetException e ) {
                return invalidDateFormat( dateFormat, e.getCause() );
            }
            catch ( Exception e ) {
                return invalidDateFormat( dateFormat, e );
            }
        }
    }

    private static class SimpleDateFormatValidator implements DateFormatValidator {

        @Override
        public DateFormatValidationResult validate(String dateFormat) {
            try {
                Class<?> aClass = Class.forName( SimpleDateFormat.class.getCanonicalName() );
                aClass.getConstructor( String.class ).newInstance( dateFormat );
                return validDateFormat( dateFormat );
            }
            catch ( InvocationTargetException e ) {
                return invalidDateFormat( dateFormat, e.getCause() );
            }
            catch ( Exception e ) {
                return invalidDateFormat( dateFormat, e );
            }
        }
    }

    private static DateFormatValidationResult validDateFormat(String dateFormat) {
        return new DateFormatValidationResult( true, Message.GENERAL_VALID_DATE, dateFormat );
    }

    private static DateFormatValidationResult invalidDateFormat(String dateFormat, Throwable e) {
        return new DateFormatValidationResult( false, Message.GENERAL_INVALID_DATE, dateFormat, e.getMessage() );
    }
}
