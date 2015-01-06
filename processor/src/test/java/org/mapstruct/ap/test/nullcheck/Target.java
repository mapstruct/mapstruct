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
package org.mapstruct.ap.test.nullcheck;

import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class Target {

    private String someObject;
    private Integer number;
    private List<String> someList;
    private MyBigIntWrapper someInteger;
    private MyLongWrapper someLong;

    public String getSomeObject() {
        return someObject;
    }

    public void setSomeObject(String someObject) {
        this.someObject = someObject;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getSomeList() {
        return someList;
    }

    public void setSomeList(List<String> someList) {
        this.someList = someList;
    }

    public MyBigIntWrapper getSomeInteger() {
        return someInteger;
    }

    public void setSomeInteger(MyBigIntWrapper someInteger) {
        this.someInteger = someInteger;
    }

    public MyLongWrapper getSomeLong() {
        return someLong;
    }

    public void setSomeLong(MyLongWrapper someLong) {
        this.someLong = someLong;
    }


}
