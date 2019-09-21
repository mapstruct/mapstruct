/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-02-12T11:25:08+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_192 (Azul Systems, Inc.)"
)
public class CustomerMapper1Impl implements CustomerMapper1 {

    @Override
    public CustomerItem map(CustomerDTO customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerItem customerItem = new CustomerItem();

        String id = customerItemId( customer );
        if ( id != null ) {
            customerItem.setId( id );
        }
        customerItem.setStatus( customerItemStatus( customer ) );
        if ( customer.getName() != null ) {
            customerItem.setName( customer.getName() );
        }
        customerItem.setLevel( customer.getLevel() );

        return customerItem;
    }

    @Override
    public CustomerDTO inverseMap(CustomerItem customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setItem( customerItemToItemDTO( customer ) );
        if ( customer.getName() != null ) {
            customerDTO.setName( customer.getName() );
        }
        customerDTO.setLevel( customer.getLevel() );

        return customerDTO;
    }

    @Override
    public void update(CustomerItem customer, CustomerDTO dto) {
        if ( customer == null ) {
            return;
        }

        if ( dto.getItem() == null ) {
            dto.setItem( new ItemDTO() );
        }
        customerItemToItemDTO1( customer, dto.getItem() );
        if ( customer.getName() != null ) {
            dto.setName( customer.getName() );
        }
        else {
            dto.setName( null );
        }
        dto.setLevel( customer.getLevel() );
    }

    @Override
    public void update(CustomerDTO dto, CustomerItem customer) {
        if ( dto == null ) {
            return;
        }

        String id = customerItemId( dto );
        if ( id != null ) {
            customer.setId( id );
        }
        else {
            customer.setId( null );
        }
        customer.setStatus( customerItemStatus( dto ) );
        if ( dto.getName() != null ) {
            customer.setName( dto.getName() );
        }
        else {
            customer.setName( null );
        }
        customer.setLevel( dto.getLevel() );
    }

    @Override
    public OrderItem map(OrderDTO order) {
        if ( order == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();

        String id = orderCustomerItemId( order );
        if ( id != null ) {
            orderItem.setId( id );
        }
        orderItem.setStatus( orderCustomerItemStatus( order ) );
        if ( order.getOrderLines() != null ) {
            for ( OrderLineDTO orderLine : order.getOrderLines() ) {
                orderItem.addOrderLine( map( orderLine ) );
            }
        }
        if ( order.getCustomer() != null ) {
            orderItem.setCustomer( map( order.getCustomer() ) );
        }

        return orderItem;
    }

    @Override
    public SaleOrder mapDoubleTarget(SaleOrderDTO order) {
        if ( order == null ) {
            return null;
        }

        SaleOrder saleOrder = new SaleOrder();

        List<OrderLineDTO> orderLines = orderOrderOrderLines( order );
        if ( orderLines != null ) {
            for ( OrderLineDTO orderLine : orderLines ) {
                saleOrder.addOrderLine( map( orderLine ) );
            }
        }
        CustomerDTO customer = orderOrderCustomer( order );
        if ( customer != null ) {
            saleOrder.setCustomer( map( customer ) );
        }
        String id = orderItemId( order );
        if ( id != null ) {
            saleOrder.setId( id );
        }
        saleOrder.setStatus( orderItemStatus( order ) );
        if ( order.getNumber() != null ) {
            saleOrder.setNumber( order.getNumber() );
        }

        return saleOrder;
    }

    @Override
    public OrderDTO map(OrderItem order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setItem( orderItemToItemDTO( order ) );
        if ( order.getCustomer() != null ) {
            orderDTO.setCustomer( inverseMap( order.getCustomer() ) );
        }
        if ( order.getOrderLines() != null ) {
            for ( OrderLine orderLine : order.getOrderLines() ) {
                orderDTO.addOrderLine( map( orderLine ) );
            }
        }

        return orderDTO;
    }

    @Override
    public OrderLineDTO map(OrderLine line) {
        if ( line == null ) {
            return null;
        }

        OrderLineDTO orderLineDTO = new OrderLineDTO();

        orderLineDTO.setLine( line.getLine() );

        return orderLineDTO;
    }

    @Override
    public OrderLine map(OrderLineDTO line) {
        if ( line == null ) {
            return null;
        }

        OrderLine orderLine = new OrderLine();

        orderLine.setLine( line.getLine() );

        return orderLine;
    }

    private String customerItemId(CustomerDTO customerDTO) {
        if ( customerDTO == null ) {
            return null;
        }
        ItemDTO item = customerDTO.getItem();
        if ( item == null ) {
            return null;
        }
        String id = item.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private int customerItemStatus(CustomerDTO customerDTO) {
        if ( customerDTO == null ) {
            return 0;
        }
        ItemDTO item = customerDTO.getItem();
        if ( item == null ) {
            return 0;
        }
        int status = item.getStatus();
        return status;
    }

    protected ItemDTO customerItemToItemDTO(CustomerItem customerItem) {
        if ( customerItem == null ) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();

        if ( customerItem.getId() != null ) {
            itemDTO.setId( customerItem.getId() );
        }
        itemDTO.setStatus( customerItem.getStatus() );

        return itemDTO;
    }

    protected void customerItemToItemDTO1(CustomerItem customerItem, ItemDTO mappingTarget) {
        if ( customerItem == null ) {
            return;
        }

        if ( customerItem.getId() != null ) {
            mappingTarget.setId( customerItem.getId() );
        }
        else {
            mappingTarget.setId( null );
        }
        mappingTarget.setStatus( customerItem.getStatus() );
    }

    private String orderCustomerItemId(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }
        CustomerDTO customer = orderDTO.getCustomer();
        if ( customer == null ) {
            return null;
        }
        ItemDTO item = customer.getItem();
        if ( item == null ) {
            return null;
        }
        String id = item.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private int orderCustomerItemStatus(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return 0;
        }
        CustomerDTO customer = orderDTO.getCustomer();
        if ( customer == null ) {
            return 0;
        }
        ItemDTO item = customer.getItem();
        if ( item == null ) {
            return 0;
        }
        int status = item.getStatus();
        return status;
    }

    private List<OrderLineDTO> orderOrderOrderLines(SaleOrderDTO saleOrderDTO) {
        if ( saleOrderDTO == null ) {
            return null;
        }
        OrderDTO order = saleOrderDTO.getOrder();
        if ( order == null ) {
            return null;
        }
        List<OrderLineDTO> orderLines = order.getOrderLines();
        if ( orderLines == null ) {
            return null;
        }
        return orderLines;
    }

    private CustomerDTO orderOrderCustomer(SaleOrderDTO saleOrderDTO) {
        if ( saleOrderDTO == null ) {
            return null;
        }
        OrderDTO order = saleOrderDTO.getOrder();
        if ( order == null ) {
            return null;
        }
        CustomerDTO customer = order.getCustomer();
        if ( customer == null ) {
            return null;
        }
        return customer;
    }

    private String orderItemId(SaleOrderDTO saleOrderDTO) {
        if ( saleOrderDTO == null ) {
            return null;
        }
        ItemDTO item = saleOrderDTO.getItem();
        if ( item == null ) {
            return null;
        }
        String id = item.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private int orderItemStatus(SaleOrderDTO saleOrderDTO) {
        if ( saleOrderDTO == null ) {
            return 0;
        }
        ItemDTO item = saleOrderDTO.getItem();
        if ( item == null ) {
            return 0;
        }
        int status = item.getStatus();
        return status;
    }

    protected ItemDTO orderItemToItemDTO(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();

        if ( orderItem.getId() != null ) {
            itemDTO.setId( orderItem.getId() );
        }
        itemDTO.setStatus( orderItem.getStatus() );

        return itemDTO;
    }
}
