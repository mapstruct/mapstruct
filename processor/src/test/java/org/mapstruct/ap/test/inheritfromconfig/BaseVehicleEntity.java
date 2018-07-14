/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig;

/**
 * @author Andreas Gudian
 */
public abstract class BaseVehicleEntity {
    private long primaryKey;
    private String auditTrail;

    public long getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(long primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(String auditTrail) {
        this.auditTrail = auditTrail;
    }
}
