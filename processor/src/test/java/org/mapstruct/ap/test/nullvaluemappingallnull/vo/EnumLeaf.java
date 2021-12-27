package org.mapstruct.ap.test.nullvaluemappingallnull.vo;

public class EnumLeaf {

    Long oid;

    Status status;

    public enum Status {
        APPROVED, REJECTED
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
