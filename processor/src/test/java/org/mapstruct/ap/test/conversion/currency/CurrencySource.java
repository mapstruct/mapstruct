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

/**
 * @author Darren Rambaud (2/16/18)
 */
public class CurrencySource {

    private Currency a;
    private Currency b;
    private Currency c;
    private Currency d;

    public Currency getA() {
        return this.a;
    }

    public void setA(final Currency a) {
        this.a = a;
    }

    public Currency getB() {
        return this.b;
    }

    public void setB(final Currency b) {
        this.b = b;
    }

    public Currency getC() {
        return this.c;
    }

    public void setC(final Currency c) {
        this.c = c;
    }

    public Currency getD() {
        return this.d;
    }

    public void setD(final Currency d) {
        this.d = d;
    }
}
