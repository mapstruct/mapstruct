/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1247;

/**
 * @author Filip Hrisafov
 */
public class OtherInternalDto {

    private String data2;
    private String expression;
    private OtherInternalData internalData;

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public OtherInternalData getInternalData() {
        return internalData;
    }

    public void setInternalData(OtherInternalData internalData) {
        this.internalData = internalData;
    }
}
