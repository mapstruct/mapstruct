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
public class Source {

    private MyObject someObject;
    private boolean hasSomeObject = true;

    private double somePrimitiveDouble;
    private boolean hasPrimitiveSomeDouble = true;

    private Integer someInteger;
    private boolean hasSomeInteger = true;

    private Long someLong1;
    private boolean hasSomeLong1 = true;

    private Long someLong2;
    private boolean hasSomeLong2 = true;

    private List<String> someList;
    private boolean hasSomeList = true;

    public boolean hasSomeObject() {
        return hasSomeObject;
    }

    public void setHasSomeObject(boolean has) {
        this.hasSomeObject = has;
    }

    public MyObject getSomeObject() {
        return someObject;
    }

    public void setSomeObject(MyObject someObject) {
        this.someObject = someObject;
    }

    public boolean hasSomePrimitiveDouble() {
        return hasPrimitiveSomeDouble;
    }

    public void setHasSomePrimitiveDouble(boolean has) {
        this.hasPrimitiveSomeDouble = has;
    }

    public double getSomePrimitiveDouble() {
        return somePrimitiveDouble;
    }

    public void setSomePrimitiveDouble(double someDouble) {
        this.somePrimitiveDouble = someDouble;
    }

    public boolean hasSomeInteger() {
        return hasSomeInteger;
    }

    public void setHasSomeInteger(boolean hasSomeInteger) {
        this.hasSomeInteger = hasSomeInteger;
    }

    public Integer getSomeInteger() {
        return someInteger;
    }

    public void setSomeInteger(Integer someInteger) {
        this.someInteger = someInteger;
    }

    public boolean hasSomeLong1() {
        return hasSomeLong1;
    }

    public void setHasSomeLong1(boolean hasSomeInLong) {
        this.hasSomeLong1 = hasSomeInLong;
    }

    public boolean hasSomeLong2() {
        return hasSomeLong2;
    }

    public void setHasSomeLong2(boolean hasSomeInLong) {
        this.hasSomeLong2 = hasSomeInLong;
    }

    public Long getSomeLong1() {
        return someLong1;
    }

    public Long getSomeLong2() {
        return someLong2;
    }

    public void setSomeLong(Long someLong) {
        this.someLong1 = someLong;
    }

    public boolean hasSomeList() {
        return hasSomeList;
    }

    public void setHasSomeList(boolean has) {
        this.hasSomeList = has;
    }

    public List<String> getSomeList() {
        return someList;
    }

    public void setSomeList(List<String> someList) {
        this.someList = someList;
    }
}
