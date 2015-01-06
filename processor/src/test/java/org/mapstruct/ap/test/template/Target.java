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
package org.mapstruct.ap.test.template;


/**
 * @author Sjaak Derksen
 */
public class Target {

    private String stringPropY;

    private Integer integerPropY;

    private String constantProp;

    private String expressionProp;

    private String nestedResultProp;

    public String getStringPropY() {
        return stringPropY;
    }

    public void setStringPropY(String stringPropY) {
        this.stringPropY = stringPropY;
    }

    public Integer getIntegerPropY() {
        return integerPropY;
    }

    public void setIntegerPropY(Integer integerPropY) {
        this.integerPropY = integerPropY;
    }

    public String getConstantProp() {
        return constantProp;
    }

    public void setConstantProp(String constantProp) {
        this.constantProp = constantProp;
    }

    public String getExpressionProp() {
        return expressionProp;
    }

    public void setExpressionProp(String expressionProp) {
        this.expressionProp = expressionProp;
    }

    public String getNestedResultProp() {
        return nestedResultProp;
    }

    public void setNestedResultProp(String nestedResultProp) {
        this.nestedResultProp = nestedResultProp;
    }

}
