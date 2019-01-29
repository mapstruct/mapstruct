/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

public class DogDTO {
    private String type;
    private AnimalDTO animal;

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public AnimalDTO getAnimal() {
        return animal;
    }

    public void setAnimal( AnimalDTO animal ) {
        this.animal = animal;
    }
}
