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
package org.mapstruct.ap.test.imports;

public class Named {

    private int foo;
    private long bar;
    private String qax;

    public int getFoo() {
        return foo;
    }

    public void setFoo(int foo) {
        this.foo = foo;
    }

    public long getBar() {
        return bar;
    }

    public void setBar(long bar) {
        this.bar = bar;
    }

    public String getQax() {
        return qax;
    }

    public void setQax(String qax) {
        this.qax = qax;
    }
}
