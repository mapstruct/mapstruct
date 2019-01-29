/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

public class AnimalDTO {
    public Integer getWeight() {
        return weight;
    }

    public void setWeight( Integer weight ) {
        this.weight = weight;
    }

    private Integer weight;

}
