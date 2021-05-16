/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2440;

import java.math.BigDecimal;
import java.util.Date;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(mappingControl = DeepClone.class)
public interface Issue2440Mapper {

    Issue2440Mapper MAPPER = Mappers.getMapper( Issue2440Mapper.class );

    @Mapping(source = "date", target = "dateStr", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "money", target = "moneyStr", numberFormat = "0.00")
    UserDto toDto(User entry);

    @InheritInverseConfiguration
    User fromDto(UserDto dto);

    class User {

        private Date date;
        private BigDecimal money;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }
    }

    class UserDto {

        private String dateStr;
        private String moneyStr;

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public String getMoneyStr() {
            return moneyStr;
        }

        public void setMoneyStr(String moneyStr) {
            this.moneyStr = moneyStr;
        }
    }
}
