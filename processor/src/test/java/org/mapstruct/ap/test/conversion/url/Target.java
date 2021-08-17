/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.url;

/**
 * @author Adam Szatyin
 */
public class Target {
    private String url;
    private String invalidURL;

    public String getURL() {
        return this.url;
    }

    public void setURL(final String url) {
        this.url = url;
    }

    public String getInvalidURL() {
        return this.invalidURL;
    }

    public void setInvalidURL(final String invalidURL) {
        this.invalidURL = invalidURL;
    }
}
