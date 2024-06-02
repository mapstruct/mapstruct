/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.qualifier;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.SourceParameterCondition;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ConditionalMethodWithSourceToTargetMapper {

    ConditionalMethodWithSourceToTargetMapper INSTANCE =
        Mappers.getMapper( ConditionalMethodWithSourceToTargetMapper.class );

    @Mapping(source = "orderDTO", target = "customer", conditionQualifiedByName = "mapCustomerFromOrder")
    Order convertToOrder(OrderDTO orderDTO);

    @Mapping(source = "customerName", target = "name")
    @Mapping(source = "orderDTO", target = "address", conditionQualifiedByName = "mapAddressFromOrder")
    Customer convertToCustomer(OrderDTO orderDTO);

    Address convertToAddress(OrderDTO orderDTO);

    @SourceParameterCondition
    @Named("mapCustomerFromOrder")
    default boolean mapCustomerFromOrder(OrderDTO orderDTO) {
        return orderDTO != null && ( orderDTO.getCustomerName() != null || mapAddressFromOrder( orderDTO ) );
    }

    @SourceParameterCondition
    @Named("mapAddressFromOrder")
    default boolean mapAddressFromOrder(OrderDTO orderDTO) {
        return orderDTO != null && ( orderDTO.getLine1() != null || orderDTO.getLine2() != null );
    }

    class OrderDTO {

        private String customerName;
        private String line1;
        private String line2;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }

    }

    class Order {

        private Customer customer;

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
    }

    class Customer {
        private String name;
        private Address address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    class Address {

        private String line1;
        private String line2;

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }
    }

}
