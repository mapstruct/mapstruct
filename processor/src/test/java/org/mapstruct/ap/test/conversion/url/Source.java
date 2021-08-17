/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.url;

import java.net.URL;

/**
 * @author Adam Szatyin
 */
public class Source {
    private URL url;

    private URL invalidURL;

    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public URL getInvalidURL() {
        return invalidURL;
    }

    public void setInvalidURL(URL invalidURL) {
        this.invalidURL = invalidURL;
    }
}
