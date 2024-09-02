/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1453;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-18T14:48:39+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
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

        Map<AuctionDto, AuctionDto> map = LinkedHashMap.newLinkedHashMap( auctions.size() );

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

        Map<? super AuctionDto, ? super AuctionDto> map = LinkedHashMap.newLinkedHashMap( auctions.size() );

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

        Map<PaymentDto, PaymentDto> map1 = LinkedHashMap.newLinkedHashMap( map.size() );

        for ( java.util.Map.Entry<Payment, Payment> entry : map.entrySet() ) {
            PaymentDto key = paymentToPaymentDto( entry.getKey() );
            PaymentDto value = paymentToPaymentDto( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
