/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._373;

/**
 *
 * @author Sjaak Derksen
 */
public class ResultDto {

    private Country countryReference;

    public Country getCountryReference() {
        return countryReference;
    }

    public void setCountryReference(Country countryReference) {
        this.countryReference = countryReference;
    }

}
