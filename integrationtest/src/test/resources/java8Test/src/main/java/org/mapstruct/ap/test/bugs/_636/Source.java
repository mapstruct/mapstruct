/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.bugs._636;

import java.math.BigInteger;

public class Source {
    private final long idFoo;
    private final String idBar;
    private final BigInteger number;

    public Source(long idFoo, String idBar, BigInteger number) {
        this.idFoo = idFoo;
        this.idBar = idBar;
        this.number = number;
    }

    public long getIdFoo() {
        return idFoo;
    }

    public String getIdBar() {
        return idBar;
    }

    public BigInteger getNumber() {
        return number;
    }
}
