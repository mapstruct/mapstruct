/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1453;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Filip Hrisafov
 */
public class AuctionDto {
    private List<PaymentDto> payments;
    private List<? super PaymentDto> otherPayments;
    private Map<PaymentDto, PaymentDto> mapPayments;
    private Map<? super PaymentDto, ? super PaymentDto> mapSuperPayments;

    List<PaymentDto> takePayments() {
        return payments;
    }

    public void setPayments(List<? extends PaymentDto> payments) {
        this.payments = payments == null ? null : new ArrayList<>( payments );
    }

    List<? super PaymentDto> takeOtherPayments() {
        return otherPayments;
    }

    public void setOtherPayments(List<? super PaymentDto> otherPayments) {
        this.otherPayments = otherPayments;
    }

    public Map<? extends PaymentDto, ? extends PaymentDto> getMapPayments() {
        return mapPayments;
    }

    public void setMapPayments(Map<? extends PaymentDto, ? extends PaymentDto> mapPayments) {
        this.mapPayments = mapPayments == null ? null : new HashMap<>( mapPayments );
    }

    public Map<? super PaymentDto, ? super PaymentDto> getMapSuperPayments() {
        return mapSuperPayments;
    }

    public void setMapSuperPayments(Map<? super PaymentDto, ? super PaymentDto> mapSuperPayments) {
        this.mapSuperPayments = mapSuperPayments;
    }
}
