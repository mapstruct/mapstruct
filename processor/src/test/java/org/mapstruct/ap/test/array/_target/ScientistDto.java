/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.array._target;

public class ScientistDto {

    //CHECKSTYLE:OFF
    public String[] publicPublications;
    public int[] publicPublicationYears;
    //CHECKSTYLE:ON

    private String name;
    private String[] publications;
    private int[] publicationYears;

    public ScientistDto() {
    }

    public ScientistDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPublications() {
        return publications;
    }

    public void setPublications(String[] publications) {
        this.publications = publications;
    }

    public int[] getPublicationYears() {
        return publicationYears;
    }

    public void setPublicationYears(int[] publicationYears) {
        this.publicationYears = publicationYears;
    }

}
