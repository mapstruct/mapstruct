/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
