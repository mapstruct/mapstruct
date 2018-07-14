/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.referenced;

/**
 * @author Andreas Gudian
 */
public class Source {
    private NotImportedDatatype notImported;

    public NotImportedDatatype getNotImported() {
        return notImported;
    }

    public void setNotImported(NotImportedDatatype notImported) {
        this.notImported = notImported;
    }
}
