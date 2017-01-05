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
package org.mapstruct.ap.test.factories.assignment;

/**
 * @author Remo Meier
 */
public class Bar5 {
    private String propA;
    private String propB;

    private final String someTypeProp0;
    private final String someTypeProp1;

    public Bar5(String someTypeProp0, String someTypeProp1) {
        this.someTypeProp0 = someTypeProp0;
        this.someTypeProp1 = someTypeProp1;
    }

    public String getPropA() {
        return propA;
    }

    public void setPropA(String propA) {
        this.propA = propA;
    }

    public String getPropB() {
        return propB;
    }

    public void setPropB(String propB) {
        this.propB = propB;
    }

    public String getSomeTypeProp0() {
        return someTypeProp0;
    }

    public String getSomeTypeProp1() {
        return someTypeProp1;
    }
}
