/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.array.source;

public class Scientist {

    //CHECKSTYLE:OFF
    public String[] publicPublications;
    public String[] publicPublicationYears;
    //CHECKSTYLE:ON
    private String name;
    private String[] publications;
    private String[] publicationYears;

    public Scientist(String name) {
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

    public String[] getPublicationYears() {
        return publicationYears;
    }

    public void setPublicationYears(String[] publicationYears) {
        this.publicationYears = publicationYears;
    }

}
