/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition;

import java.util.Date;

public class BoxEntity {

    private Date creationDate;
    private Long id;
    private String name;
    private String label;
    private ShelveEntity shelve;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ShelveEntity getShelve() {
        return shelve;
    }

    public void setShelve(ShelveEntity shelve) {
        this.shelve = shelve;
    }
}
