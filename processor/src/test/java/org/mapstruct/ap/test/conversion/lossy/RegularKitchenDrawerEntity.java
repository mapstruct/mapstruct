/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

/**
 *
 * @author Sjaak Derksen
 */
public class RegularKitchenDrawerEntity {

    private int numberOfForks;
    private Integer numberOfKnifes;
    private Long numberOfSpoons;
    private float depth;
    private Float length;
    private VerySpecialNumber height;
    private int drawerId;

    public int getNumberOfForks() {
        return numberOfForks;
    }

    public void setNumberOfForks(int numberOfForks) {
        this.numberOfForks = numberOfForks;
    }

    public Integer getNumberOfKnifes() {
        return numberOfKnifes;
    }

    public void setNumberOfKnifes(Integer numberOfKnifes) {
        this.numberOfKnifes = numberOfKnifes;
    }

    public Long getNumberOfSpoons() {
        return numberOfSpoons;
    }

    public void setNumberOfSpoons(Long numberOfSpoons) {
        this.numberOfSpoons = numberOfSpoons;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public VerySpecialNumber getHeight() {
        return height;
    }

    public void setHeight(VerySpecialNumber height) {
        this.height = height;
    }

    public int getDrawerId() {
        return drawerId;
    }

    public void setDrawerId(int drawerId) {
        this.drawerId = drawerId;
    }
}
