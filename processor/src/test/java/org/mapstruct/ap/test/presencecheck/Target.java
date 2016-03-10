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
package org.mapstruct.ap.test.presencecheck;

import java.util.List;

/**
 * @author Sean Huang
 */
public class Target {

    private MyObject someObject;
    private double somePrimitiveDouble;
    private Integer someInteger;
    private MyLongWrapper someLong1;
    private MyLongWrapper someLong2;
    private List<String> someList;

    public MyObject getSomeObject() {
        return someObject;
    }

    public void setSomeObject(MyObject someObject) {
        this.someObject = someObject;
    }

    public double getSomePrimitiveDouble() {
        return somePrimitiveDouble;
    }

    public void setSomePrimitiveDouble(double someDouble) {
        this.somePrimitiveDouble = someDouble;
    }

    public Integer getSomeInteger() {
        return someInteger;
    }

    public void setSomeInteger(Integer someInteger) {
        this.someInteger = someInteger;
    }

    public MyLongWrapper getSomeLong1() {
        return someLong1;
    }

    public void setSomeLong1(MyLongWrapper someLong) {
        this.someLong1 = someLong;
    }

    public MyLongWrapper getSomeLong2() {
        return someLong2;
    }

    public void setSomeLong2(MyLongWrapper someLong) {
        this.someLong2 = someLong;
    }

    public List<String> getSomeList() {
        return someList;
    }

    public void setSomeList(List<String> someList) {
        this.someList = someList;
    }
}
