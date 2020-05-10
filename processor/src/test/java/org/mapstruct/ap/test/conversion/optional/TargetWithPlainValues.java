/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import java.util.Set;

public class TargetWithPlainValues {

    private String string;

    private String number;

    private String optional;

    private Set<String> optionalsSet;

    public String getString() {
        return this.string;
    }

    public void setString(final String string) {
        this.string = string;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getOptional() {
        return this.optional;
    }

    public void setOptional(final String optional) {
        this.optional = optional;
    }

    public Set<String> getOptionalsSet() {
        return this.optionalsSet;
    }

    public void setOptionalsSet(final Set<String> optionalsSet) {
        this.optionalsSet = optionalsSet;
    }

}
