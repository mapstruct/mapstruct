/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.bean;

import org.mapstruct.Named;
import org.mapstruct.ap.test.selection.qualifier.annotation.CreateGermanRelease;

/**
 *
 * @author Sjaak Derksen
 */
public class ReleaseFactory {

    public OriginalRelease createOriginalRelease() {
        return new OriginalRelease();
    }

    @CreateGermanRelease
    @Named( "CreateGermanRelease" )
    public GermanRelease createGermanRelease() {
        return new GermanRelease();
    }
}
