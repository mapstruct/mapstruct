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
