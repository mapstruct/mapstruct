/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.immutable;

import org.mapstruct.Constructor;

/**
 * @author Ciaran Liedeman
 */
public class Target {

    private final String a;
    private final int b;
    private final short c;
    private double d;

    @Constructor( {"a", "b", "c"} )
    public Target(String a, int b, short c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public short getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

}
