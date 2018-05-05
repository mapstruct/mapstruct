/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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

}
