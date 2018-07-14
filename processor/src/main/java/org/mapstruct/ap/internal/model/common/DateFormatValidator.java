/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

/**
 * An abstraction for validating {@link ConversionContext#getDateFormat()}. There are implementers for different date
 * types such as Joda Time, Java8 Time and java.util.Date.
 */
interface DateFormatValidator {

    /**
     * Validate the given dateFormat.
     *
     * @param dateFormat string supposed to be used for date formatting and parsing.
     *
     * @return {@link DateFormatValidationResult} representing the validation result.
     */
    DateFormatValidationResult validate(String dateFormat);

}
