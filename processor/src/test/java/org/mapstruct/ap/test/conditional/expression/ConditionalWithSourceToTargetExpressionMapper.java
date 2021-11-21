/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.expression;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(imports = ConditionalWithSourceToTargetExpressionMapper.Util.class)
public interface ConditionalWithSourceToTargetExpressionMapper {

    ConditionalWithSourceToTargetExpressionMapper INSTANCE =
        Mappers.getMapper( ConditionalWithSourceToTargetExpressionMapper.class );

    @Mapping(source = "orderDTO", target = "customer",
        conditionExpression = "java(Util.mapCustomerFromOrder( orderDTO ))")
    Order convertToOrder(OrderDTO orderDTO);

    @Mapping(source = "customerName", target = "name")
    @Mapping(source = "orderDTO", target = "address",
        conditionExpression = "java(Util.mapAddressFromOrder( orderDTO ))")
    Customer convertToCustomer(OrderDTO orderDTO);

    Address convertToAddress(OrderDTO orderDTO);

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

    interface Util {

        static boolean mapCustomerFromOrder(OrderDTO orderDTO) {
            return orderDTO != null && ( orderDTO.getCustomerName() != null || mapAddressFromOrder( orderDTO ) );
        }

        static boolean mapAddressFromOrder(OrderDTO orderDTO) {
            return orderDTO != null && ( orderDTO.getLine1() != null || orderDTO.getLine2() != null );
        }

    }

}
