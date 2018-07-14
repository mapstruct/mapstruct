/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.currency;

import java.util.Currency;
import java.util.Set;

/**
 * @author Darren Rambaud
 */
public class CurrencySource {

    private Currency currencyA;
    private Set<Currency> uniqueCurrencies;

    public Currency getCurrencyA() {
        return this.currencyA;
    }

    public void setCurrencyA(final Currency currencyA) {
        this.currencyA = currencyA;
    }

    public Set<Currency> getUniqueCurrencies() {
        return this.uniqueCurrencies;
    }

    public void setUniqueCurrencies(final Set<Currency> uniqueCurrencies) {
        this.uniqueCurrencies = uniqueCurrencies;
    }
}
