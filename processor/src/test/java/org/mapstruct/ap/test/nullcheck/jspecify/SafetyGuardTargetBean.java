/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

public class SafetyGuardTargetBean {

    private int unboxedNumber;
    private String textWithDefault;
    private String textWithNvpms;

    public int getUnboxedNumber() {
        return unboxedNumber;
    }

    public void setUnboxedNumber(int unboxedNumber) {
        this.unboxedNumber = unboxedNumber;
    }

    public String getTextWithDefault() {
        return textWithDefault;
    }

    public void setTextWithDefault(String textWithDefault) {
        this.textWithDefault = textWithDefault;
    }

    public String getTextWithNvpms() {
        return textWithNvpms;
    }

    public void setTextWithNvpms(String textWithNvpms) {
        this.textWithNvpms = textWithNvpms;
    }
}
