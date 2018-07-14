/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.currency;

import java.util.Set;

/**
 * @author Darren Rambaud
 */
public class CurrencyTarget {

    private String currencyA;
    private Set<String> uniqueCurrencies;

    public String getCurrencyA() {
        return this.currencyA;
    }

    public void setCurrencyA(final String currencyA) {
        this.currencyA = currencyA;
    }

    public Set<String> getUniqueCurrencies() {
        return this.uniqueCurrencies;
    }

    public void setUniqueCurrencies(final Set<String> uniqueCurrencies) {
        this.uniqueCurrencies = uniqueCurrencies;
    }
}
