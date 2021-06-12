/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2478;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2478Mapper {

    Issue2478Mapper INSTANCE = Mappers.getMapper( Issue2478Mapper.class );

    ProductEntity productFromDto(Product dto, @Context Shop shop);

    class Product {
        protected final String name;
        protected final Shop shop;

        public Product(String name, Shop shop) {
            this.name = name;
            this.shop = shop;
        }

        public String getName() {
            return name;
        }

        public Shop getShop() {
            return shop;
        }
    }

    class Shop {
        protected final String name;

        public Shop(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class ProductEntity {

        protected String name;
        protected ShopEntity shop;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ShopEntity getShop() {
            return shop;
        }

        public void setShop(ShopEntity shop) {
            this.shop = shop;
        }
    }

    class ShopEntity {
        protected String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
