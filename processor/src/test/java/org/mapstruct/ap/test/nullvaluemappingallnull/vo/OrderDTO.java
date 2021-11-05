package org.mapstruct.ap.test.nullvaluemappingallnull.vo;

import java.util.List;

public class OrderDTO {

    String orderId;

    String addressLine1, addressLine2, addressLine3;

    Long leafOid;

    String defaultLeafName;

    List<OrderItemDTO> orderItems;

    public enum Status {
        APPROVED, REJECTED
    }

    Status enumLeafStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public Long getLeafOid() {
        return leafOid;
    }

    public void setLeafOid(Long leafOid) {
        this.leafOid = leafOid;
    }

    public String getDefaultLeafName() {
        return defaultLeafName;
    }

    public void setDefaultLeafName(String defaultLeafName) {
        this.defaultLeafName = defaultLeafName;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public Status getEnumLeafStatus() {
        return enumLeafStatus;
    }

    public void setEnumLeafStatus(Status enumLeafStatus) {
        this.enumLeafStatus = enumLeafStatus;
    }

}

