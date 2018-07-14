/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass;

/**
 * @author Andreas Gudian
 */
public class AbstractDto implements Identifiable {

    //CHECKSTYLE:OFF
    public Long publicId;
    //CHECKSTYLE:ON
    private Long id = 1L;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
