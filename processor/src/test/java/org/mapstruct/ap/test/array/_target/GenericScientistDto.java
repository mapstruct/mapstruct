/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.array._target;

public class GenericScientistDto<T> {

    //CHECKSTYLE:OFF
    public T[] publicPublications;
    public int[] publicPublicationYears;
    //CHECKSTYLE:ON

    private T name;
    private T[] publications;
    private int[] publicationYears;

    public GenericScientistDto() {
    }

    public GenericScientistDto(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public T[] getPublications() {
        return publications;
    }

    public void setPublications(T[] publications) {
        this.publications = publications;
    }

    public int[] getPublicationYears() {
        return publicationYears;
    }

    public void setPublicationYears(int[] publicationYears) {
        this.publicationYears = publicationYears;
    }

}
