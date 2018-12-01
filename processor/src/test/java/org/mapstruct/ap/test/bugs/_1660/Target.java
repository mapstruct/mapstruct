/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1660;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private static String staticValue = "targetStatic";
    private static List<String> otherStaticValues = new ArrayList<>( Collections.singletonList( "targetOtherStatic" ) );

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getStaticValue() {
        return staticValue;
    }

    public static void setStaticValue(String staticValue) {
        Target.staticValue = staticValue;
    }

    public static List<String> getOtherStaticValues() {
        return otherStaticValues;
    }

    public static void addOtherStaticValue(String otherStaticValue) {
        Target.otherStaticValues.add( otherStaticValue );
    }

    public static void setOtherStaticValues(List<String> otherStaticValues) {
        Target.otherStaticValues = otherStaticValues;
    }
}
