/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore.expand;

/**
 *
 * @author Sjaak Derksen
 */
public class Wrench {

    boolean bahco;
    String description;

    public boolean isBahco() {
        return bahco;
    }

    public void setBahco(boolean bahco) {
        this.bahco = bahco;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
