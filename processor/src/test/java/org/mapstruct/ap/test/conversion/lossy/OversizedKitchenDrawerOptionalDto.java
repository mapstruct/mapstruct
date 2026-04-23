/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalLong;

/**
 * @author Filip Hrisafov
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OversizedKitchenDrawerOptionalDto {

    /* yes, its a big drawer */
    private OptionalLong numberOfForks;
    private Optional<BigInteger> numberOfKnifes;
    private Optional<VerySpecialNumber> numberOfSpoons;
    private Optional<Double> depth;
    private Optional<BigDecimal> length;
    private OptionalDouble height;
    private Optional<String> drawerId;

    public OptionalLong getNumberOfForks() {
        return numberOfForks;
    }

    public void setNumberOfForks(OptionalLong numberOfForks) {
        this.numberOfForks = numberOfForks;
    }

    public Optional<BigInteger> getNumberOfKnifes() {
        return numberOfKnifes;
    }

    public void setNumberOfKnifes(Optional<BigInteger> numberOfKnifes) {
        this.numberOfKnifes = numberOfKnifes;
    }

    public Optional<VerySpecialNumber> getNumberOfSpoons() {
        return numberOfSpoons;
    }

    public void setNumberOfSpoons(Optional<VerySpecialNumber> numberOfSpoons) {
        this.numberOfSpoons = numberOfSpoons;
    }

    public Optional<Double> getDepth() {
        return depth;
    }

    public void setDepth(Optional<Double> depth) {
        this.depth = depth;
    }

    public Optional<BigDecimal> getLength() {
        return length;
    }

    public void setLength(Optional<BigDecimal> length) {
        this.length = length;
    }

    public OptionalDouble getHeight() {
        return height;
    }

    public void setHeight(OptionalDouble height) {
        this.height = height;
    }

    public Optional<String> getDrawerId() {
        return drawerId;
    }

    public void setDrawerId(Optional<String> drawerId) {
        this.drawerId = drawerId;
    }
}
