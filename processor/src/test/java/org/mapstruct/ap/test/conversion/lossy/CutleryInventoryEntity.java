/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

public class CutleryInventoryEntity {

    private int numberOfKnifes;
    private Long numberOfForks;
    private short numberOfSpoons;
    private String drawerId;

    private double approximateKnifeLength;

    public int getNumberOfKnifes() {
        return numberOfKnifes;
    }

    public void setNumberOfKnifes(int numberOfKnifes) {
        this.numberOfKnifes = numberOfKnifes;
    }

    public Long getNumberOfForks() {
        return numberOfForks;
    }

    public void setNumberOfForks(Long numberOfForks) {
        this.numberOfForks = numberOfForks;
    }

    public short getNumberOfSpoons() {
        return numberOfSpoons;
    }

    public void setNumberOfSpoons(short numberOfSpoons) {
        this.numberOfSpoons = numberOfSpoons;
    }

    public double getApproximateKnifeLength() {
        return approximateKnifeLength;
    }

    public void setApproximateKnifeLength(double approximateKnifeLength) {
        this.approximateKnifeLength = approximateKnifeLength;
    }

    public String getDrawerId() {
        return drawerId;
    }

    public void setDrawerId(String drawerId) {
        this.drawerId = drawerId;
    }
}
