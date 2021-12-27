package org.mapstruct.ap.test.nullvaluemappingallnull.vo;

import java.util.List;

public class Order {

    Address address;

    DefaultLeaf defaultLeaf;

    NestedMiddle nestedMiddle;

    NestedCollection nestedCollection;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DefaultLeaf getDefaultLeaf() {
        return defaultLeaf;
    }

    public void setDefaultLeaf(DefaultLeaf defaultLeaf) {
        this.defaultLeaf = defaultLeaf;
    }

    public NestedMiddle getNestedMiddle() {
        return nestedMiddle;
    }

    public void setNestedMiddle(NestedMiddle nestedMiddle) {
        this.nestedMiddle = nestedMiddle;
    }

}

