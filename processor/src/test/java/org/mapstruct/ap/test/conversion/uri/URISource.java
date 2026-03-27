/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uri;

import java.net.URI;

/**
 * @author Maciej Kucharczyk
 */
public class URISource {
    private URI uriA;

    private URI invalidURI;

    public URI getUriA() {
        return uriA;
    }

    public void setUriA(URI uriA) {
        this.uriA = uriA;
    }

    public URI getInvalidURI() {
        return invalidURI;
    }

    public void setInvalidURI(URI invalidURI) {
        this.invalidURI = invalidURI;
    }
}
