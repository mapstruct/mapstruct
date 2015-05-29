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

    public void printErrorMessage(FormattingMessager messager) {
        messager.printMessage( validationInfo, validationInfoArgs );
    }

}
