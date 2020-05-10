/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import java.util.Optional;
import java.util.Set;

public class SourceWithOptional {

    private String string;

    private Integer number;

    private Optional<String> optional;

    private Set<Optional<String>> optionalsSet;

    public String getString() {
        return this.string;
    }

    public void setString(final String string) {
        this.string = string;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public Optional<String> getOptional() {
        return this.optional;
    }

    public void setOptional(final Optional<String> optional) {
        this.optional = optional;
    }

    public Set<Optional<String>> getOptionalsSet() {
        return this.optionalsSet;
    }

    public void setOptionalsSet(final Set<Optional<String>> optionalsSet) {
        this.optionalsSet = optionalsSet;
    }

}
