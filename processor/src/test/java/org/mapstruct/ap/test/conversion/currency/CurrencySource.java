/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.conversion.currency;

import java.util.Currency;
import java.util.Set;

/**
 * @author Darren Rambaud (2/16/18)
 */
public class CurrencySource {

    private Currency currencyA;
    private Currency currencyB;
    private Currency currencyC;
    private Currency currencyD;
    private Set<Currency> uniqueCurrencies;

    public Currency getCurrencyA() {
        return this.currencyA;
    }

    public void setCurrencyA(final Currency currencyA) {
        this.currencyA = currencyA;
    }

    public Currency getCurrencyB() {
        return this.currencyB;
    }

    public void setCurrencyB(final Currency currencyB) {
        this.currencyB = currencyB;
    }

    public Currency getCurrencyC() {
        return this.currencyC;
    }

    public void setCurrencyC(final Currency currencyC) {
        this.currencyC = currencyC;
    }

    public Currency getCurrencyD() {
        return this.currencyD;
    }

    public void setCurrencyD(final Currency currencyD) {
        this.currencyD = currencyD;
    }

    public Set<Currency> getUniqueCurrencies() {
        return this.uniqueCurrencies;
    }

    public void setUniqueCurrencies(final Set<Currency> uniqueCurrencies) {
        this.uniqueCurrencies = uniqueCurrencies;
    }
}
