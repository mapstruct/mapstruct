/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.mixed.source;

/**
 *
 * @author Sjaak Derksen
 */
public class Interior {

    private String designer;
    private Ornament ornament;

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public Ornament getOrnament() {
        return ornament;
    }

    public void setOrnament(Ornament ornament) {
        this.ornament = ornament;
    }
}
