/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1558.java8;

public class Car2 {
    private boolean[] booleanData;
    private byte[] data;
    private short[] shortData;
    private int[] intData;
    private long[] longData;
    private char[] charData;
    private float[] floatData;
    private double[] doubleData;

    @NotNull
    public boolean[] getBooleanData() {
        return booleanData;
    }

    public void setBooleanData(boolean[] booleanData) {
        this.booleanData = booleanData;
    }

    @NotNull
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @NotNull
    public short[] getShortData() {
        return shortData;
    }

    public void setShortData(short[] shortData) {
        this.shortData = shortData;
    }

    @NotNull
    public int[] getIntData() {
        return intData;
    }

    public void setIntData(int[] intData) {
        this.intData = intData;
    }

    @NotNull
    public long[] getLongData() {
        return longData;
    }

    public void setLongData(long[] longData) {
        this.longData = longData;
    }

    @NotNull
    public char[] getCharData() {
        return charData;
    }

    public void setCharData(char[] charData) {
        this.charData = charData;
    }

    @NotNull
    public float[] getFloatData() {
        return floatData;
    }

    public void setFloatData(float[] floatData) {
        this.floatData = floatData;
    }

    @NotNull
    public double[] getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(double[] doubleData) {
        this.doubleData = doubleData;
    }
}
