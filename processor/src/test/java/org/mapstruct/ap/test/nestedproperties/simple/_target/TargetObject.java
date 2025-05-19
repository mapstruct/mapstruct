/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedproperties.simple._target;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public class TargetObject {

    private long publicLongValue;
    private long longValue;
    private int intValue;
    private double doubleValue;
    private float floatValue;
    private short shortValue;
    private char charValue;
    private byte byteValue;
    private boolean booleanValue;

    private final Optional<String> someString;
    private final Optional<Integer> someInteger;
    private final Optional<Double> someDouble;
    private final Optional<Boolean> someBoolean;
    private final OptionalInt someIntValue;
    private final OptionalDouble someDoubleValue;
    private final OptionalLong someLongValue;

    public TargetObject(Optional<String> someString, Optional<Integer> someInteger, Optional<Double> someDouble,
                      Optional<Boolean> someBoolean, OptionalInt someIntValue, OptionalDouble someDoubleValue, OptionalLong someLongValue) {
        this.someString = someString;
        this.someInteger = someInteger;
        this.someDouble = someDouble;
        this.someBoolean = someBoolean;
        this.someIntValue = someIntValue;
        this.someDoubleValue = someDoubleValue;
        this.someLongValue = someLongValue;
    }

    private byte[] byteArray;

    private String stringValue;

    public long getPublicLongValue() {
        return publicLongValue;
    }

    public void setPublicLongValue(long publicLongValue) {
        this.publicLongValue = publicLongValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }


    public Optional<String> getSomeString() {
        return someString;
    }

    public Optional<Integer> getSomeInteger() {
        return someInteger;
    }

    public Optional<Double> getSomeDouble() {
        return someDouble;
    }

    public Optional<Boolean> getSomeBoolean() {
        return someBoolean;
    }

    public OptionalLong getSomeLongValue() {
        return someLongValue;
    }

    public OptionalDouble getSomeDoubleValue() {
        return someDoubleValue;
    }

    public OptionalInt getSomeIntValue() {
        return someIntValue;
    }
}
