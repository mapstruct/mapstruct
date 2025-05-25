/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Sjaak Derksen
 */
public class OversizedKitchenDrawerDto {

    /* yes, its a big drawer */
    private long numberOfForks;
    private BigInteger numberOfKnifes;
    private VerySpecialNumber numberOfSpoons;
    private Double depth;
    private BigDecimal length;
    private double height;
    private String drawerId;

    public long getNumberOfForks() {
        return numberOfForks;
    }

    public void setNumberOfForks(long numberOfForks) {
        this.numberOfForks = numberOfForks;
    }

    public BigInteger getNumberOfKnifes() {
        return numberOfKnifes;
    }

    public void setNumberOfKnifes(BigInteger numberOfKnifes) {
        this.numberOfKnifes = numberOfKnifes;
    }

    public VerySpecialNumber getNumberOfSpoons() {
        return numberOfSpoons;
    }

    public void setNumberOfSpoons(VerySpecialNumber numberOfSpoons) {
        this.numberOfSpoons = numberOfSpoons;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getDrawerId() {
        return drawerId;
    }

    public void setDrawerId(String drawerId) {
        this.drawerId = drawerId;
    }
}
