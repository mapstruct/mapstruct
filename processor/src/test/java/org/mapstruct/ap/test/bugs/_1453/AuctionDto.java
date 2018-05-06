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
        this.payments = payments == null ? null : new ArrayList<PaymentDto>( payments );
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
        this.mapPayments = mapPayments == null ? null : new HashMap<PaymentDto, PaymentDto>( mapPayments );
    }

    public Map<? super PaymentDto, ? super PaymentDto> getMapSuperPayments() {
        return mapSuperPayments;
    }

    public void setMapSuperPayments(Map<? super PaymentDto, ? super PaymentDto> mapSuperPayments) {
        this.mapSuperPayments = mapSuperPayments;
    }
}
