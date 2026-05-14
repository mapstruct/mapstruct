/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.array.source;

public class GenericScientist<T> {

    //CHECKSTYLE:OFF
    public T[] publicPublications;
    public T[] publicPublicationYears;
    //CHECKSTYLE:ON
    private T name;
    private T[] publications;
    private T[] publicationYears;

    public GenericScientist(T name) {
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

    public T[] getPublicationYears() {
        return publicationYears;
    }

    public void setPublicationYears(T[] publicationYears) {
        this.publicationYears = publicationYears;
    }

}
