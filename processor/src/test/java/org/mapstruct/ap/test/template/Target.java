/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
