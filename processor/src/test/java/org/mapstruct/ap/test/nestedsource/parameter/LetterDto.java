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
public class LetterDto {

    private FontDto font;
    private String heading;
    private String body;
    private String signature;

    public FontDto getFont() {
        return font;
    }

    public void setFont(FontDto font) {
        this.font = font;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
