/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

public class SourceExt2 extends SourceBase {

    private Long baz;

    public Long getBaz() {
        return baz;
    }

    public void setBaz(Long baz) {
        this.baz = baz;
    }
}
