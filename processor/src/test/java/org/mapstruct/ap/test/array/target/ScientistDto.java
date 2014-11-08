package org.mapstruct.ap.test.array.target;

public class ScientistDto {

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
