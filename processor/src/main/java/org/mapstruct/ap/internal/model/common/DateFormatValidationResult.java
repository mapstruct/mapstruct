/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Reflects the result of a date format validation
 */
final class DateFormatValidationResult {

    private final boolean isValid;
    private final Message validationInfo;
    private final Object[] validationInfoArgs;

    /**
     * Create a new instance.
     *
     * @param isValid determines of the validation was successful.
     * @param validationInformation a string representing the validation result
     */
    DateFormatValidationResult(boolean isValid, Message validationInformation, Object... infoArgs) {

        this.isValid = isValid;
        this.validationInfo = validationInformation;
        this.validationInfoArgs = infoArgs;
    }

    public boolean isValid() {
        return isValid;
    }

    /**
     * Print the error with the most specific information possible.
     *
     * @param messager the messager to print the error message to
     * @param element the element that had the error
     * @param annotation the mirror of the annotation that had an error
     * @param value the value of the annotation that had an error
     */
    public void printErrorMessage(FormattingMessager messager, Element element, AnnotationMirror annotation,
        AnnotationValue value) {
        messager.printMessage( element, annotation, value, validationInfo, validationInfoArgs );
    }

}
