/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1453;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1453Mapper {

    Issue1453Mapper INSTANCE = Mappers.getMapper( Issue1453Mapper.class );

    AuctionDto map(Auction auction);

    List<AuctionDto> mapExtend(List<? extends Auction> auctions);

    List<? super AuctionDto> mapSuper(List<Auction> auctions);

    Map<AuctionDto, AuctionDto> mapExtend(Map<? extends Auction, ? extends Auction> auctions);

    Map<? super AuctionDto, ? super AuctionDto> mapSuper(Map<Auction, Auction> auctions);
}
