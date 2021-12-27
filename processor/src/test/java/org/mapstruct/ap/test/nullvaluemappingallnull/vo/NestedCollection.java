package org.mapstruct.ap.test.nullvaluemappingallnull.vo;

import java.util.List;

public class NestedCollection {

    Long oid;

    List<OrderItem> orderItems;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
