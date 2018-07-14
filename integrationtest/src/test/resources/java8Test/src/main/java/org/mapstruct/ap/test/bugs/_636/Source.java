/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._636;

public class Source {
    private final long idFoo;
    private final String idBar;

    public Source(long idFoo, String idBar) {
        this.idFoo = idFoo;
        this.idBar = idBar;
    }

    public long getIdFoo() {
        return idFoo;
    }

    public String getIdBar() {
        return idBar;
    }
}
