/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsource.parameter;

/**
 *
 * @author Sjaak Derksen
 */
public class LetterEntity {

    private String fontType;
    private int fontSize;
    private String letterHeading;
    private String letterBody;
    private String letterSignature;

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getLetterHeading() {
        return letterHeading;
    }

    public void setLetterHeading(String letterHeading) {
        this.letterHeading = letterHeading;
    }

    public String getLetterBody() {
        return letterBody;
    }

    public void setLetterBody(String letterBody) {
        this.letterBody = letterBody;
    }

    public String getLetterSignature() {
        return letterSignature;
    }

    public void setLetterSignature(String letterSignature) {
        this.letterSignature = letterSignature;
    }
}
