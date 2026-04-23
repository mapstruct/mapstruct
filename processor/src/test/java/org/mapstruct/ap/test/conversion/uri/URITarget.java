/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uri;

/**
 * @author Maciej Kucharczyk
 */
public class URITarget {
    private String uriA;
    private String invalidURI;

    public String getUriA() {
        return uriA;
    }

    public void setUriA(String uriA) {
        this.uriA = uriA;
    }

    public String getInvalidURI() {
        return invalidURI;
    }

    public void setInvalidURI(String invalidURI) {
        this.invalidURI = invalidURI;
    }
}
