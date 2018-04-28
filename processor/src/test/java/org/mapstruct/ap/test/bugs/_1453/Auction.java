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
package org.mapstruct.ap.test.bugs._1453;

import java.util.List;
import java.util.Map;

/**
 * @author Filip Hrisafov
 */
public class Auction {

    private final List<Payment> payments;
    private final List<Payment> otherPayments;
    private Map<Payment, Payment> mapPayments;
    private Map<Payment, Payment> mapSuperPayments;

    public Auction(List<Payment> payments, List<Payment> otherPayments) {
        this.payments = payments;
        this.otherPayments = otherPayments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<Payment> getOtherPayments() {
        return otherPayments;
    }

    public Map<Payment, Payment> getMapPayments() {
        return mapPayments;
    }

    public void setMapPayments(Map<Payment, Payment> mapPayments) {
        this.mapPayments = mapPayments;
    }

    public Map<Payment, Payment> getMapSuperPayments() {
        return mapSuperPayments;
    }

    public void setMapSuperPayments(Map<Payment, Payment> mapSuperPayments) {
        this.mapSuperPayments = mapSuperPayments;
    }
}
