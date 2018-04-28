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
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-05-04T23:25:01+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
public class Issue1453MapperImpl implements Issue1453Mapper {

    @Override
    public AuctionDto map(Auction auction) {
        if ( auction == null ) {
            return null;
        }

        AuctionDto auctionDto = new AuctionDto();

        auctionDto.setPayments( paymentListToPaymentDtoList( auction.getPayments() ) );
        auctionDto.setOtherPayments( paymentListToPaymentDtoList( auction.getOtherPayments() ) );
        auctionDto.setMapPayments( paymentPaymentMapToPaymentDtoPaymentDtoMap( auction.getMapPayments() ) );
        auctionDto.setMapSuperPayments( paymentPaymentMapToPaymentDtoPaymentDtoMap( auction.getMapSuperPayments() ) );

        return auctionDto;
    }

    @Override
    public List<AuctionDto> mapExtend(List<? extends Auction> auctions) {
        if ( auctions == null ) {
            return null;
        }

        List<AuctionDto> list = new ArrayList<AuctionDto>( auctions.size() );
        for ( Auction auction : auctions ) {
            list.add( map( auction ) );
        }

        return list;
    }

    @Override
    public List<? super AuctionDto> mapSuper(List<Auction> auctions) {
        if ( auctions == null ) {
            return null;
        }

        List<? super AuctionDto> list = new ArrayList<AuctionDto>( auctions.size() );
        for ( Auction auction : auctions ) {
            list.add( map( auction ) );
        }

        return list;
    }

    @Override
    public Map<AuctionDto, AuctionDto> mapExtend(Map<? extends Auction, ? extends Auction> auctions) {
        if ( auctions == null ) {
            return null;
        }

        Map<AuctionDto, AuctionDto> map = new HashMap<AuctionDto, AuctionDto>( Math.max( (int) ( auctions.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<? extends Auction, ? extends Auction> entry : auctions.entrySet() ) {
            AuctionDto key = map( entry.getKey() );
            AuctionDto value = map( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    @Override
    public Map<? super AuctionDto, ? super AuctionDto> mapSuper(Map<Auction, Auction> auctions) {
        if ( auctions == null ) {
            return null;
        }

        Map<? super AuctionDto, ? super AuctionDto> map = new HashMap<AuctionDto, AuctionDto>( Math.max( (int) ( auctions.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Auction, Auction> entry : auctions.entrySet() ) {
            AuctionDto key = map( entry.getKey() );
            AuctionDto value = map( entry.getValue() );
            map.put( key, value );
        }

        return map;
    }

    protected PaymentDto paymentToPaymentDto(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setPrice( payment.getPrice() );

        return paymentDto;
    }

    protected List<PaymentDto> paymentListToPaymentDtoList(List<Payment> list) {
        if ( list == null ) {
            return null;
        }

        List<PaymentDto> list1 = new ArrayList<PaymentDto>( list.size() );
        for ( Payment payment : list ) {
            list1.add( paymentToPaymentDto( payment ) );
        }

        return list1;
    }

    protected Map<PaymentDto, PaymentDto> paymentPaymentMapToPaymentDtoPaymentDtoMap(Map<Payment, Payment> map) {
        if ( map == null ) {
            return null;
        }

        Map<PaymentDto, PaymentDto> map1 = new HashMap<PaymentDto, PaymentDto>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Payment, Payment> entry : map.entrySet() ) {
            PaymentDto key = paymentToPaymentDto( entry.getKey() );
            PaymentDto value = paymentToPaymentDto( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
